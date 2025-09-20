package com.example.milewebassignment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.example.milewebassignment.auth.AuthState
import com.example.milewebassignment.models.Track
import com.example.milewebassignment.player.AndroidPlayerController
import com.example.milewebassignment.ui.screens.DashboardScreen
import com.example.milewebassignment.ui.screens.LoginScreen
import com.example.milewebassignment.ui.screens.PlayerScreen
import com.example.milewebassignment.ui.theme.AppTheme
import com.example.milewebassignment.viewmodel.AuthViewModel
import com.example.milewebassignment.viewmodel.MusicViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    private val authViewModel = AuthViewModel()
    private val musicViewModel = MusicViewModel()
    private lateinit var playerController: AndroidPlayerController

    private val httpClient by lazy {
        HttpClient(CIO) { install(ContentNegotiation) { json() } }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerController = AndroidPlayerController(this)

        setContent {
            AppTheme {
                val loggedIn by remember { derivedStateOf { authViewModel.isLoggedIn } } // simple
                // central UI decision based on AuthViewModel
                var showPlayerFor by remember { mutableStateOf<Track?>(null) }
                var isPlaying by remember { mutableStateOf(false) }
                var positionMs by remember { mutableStateOf(0L) }
                var durationMs by remember { mutableStateOf(0L) }

                // Observe selected track
                val currentTrack by musicViewModel.currentTrack.collectAsState()

                // If track selected -> play
                LaunchedEffect(currentTrack) {
                    currentTrack?.let { t ->
                        try {
                            playerController.play(t)
                            isPlaying = true
                            durationMs = playerController.duration
                        } catch (ex: Exception) { Log.e(TAG, "play failed", ex) }
                        showPlayerFor = t
                    }
                }

                // Poll playback position
                LaunchedEffect(isPlaying) {
                    if (isPlaying) {
                        while (isPlaying) {
                            try {
                                positionMs = playerController.position
                            } catch (_: Exception) {}
                            kotlinx.coroutines.delay(400)
                        }
                    }
                }

                if (AuthState.hasToken().not()) {
                    // show login screen (we use mock login here)
                    LoginScreen(authViewModel) { url ->
                        // open OAuth, replace client id with BuildConfig value if needed
                        val finalUrl = url.replace("{CLIENT_ID}", BuildConfig.GITLAB_CLIENT_ID)
                        openCustomTab(finalUrl)
                    }
                } else {
                    // Dashboard
                    DashboardScreen(authViewModel, musicViewModel) { track ->
                        // selects and plays
                        musicViewModel.selectTrack(track)
                    }
                }

                // Player screen overlay if playing
                showPlayerFor?.let { track ->
                    PlayerScreen(
                        track = Track(track.id, track.title, track.artist, track.artUrl, track.streamUrl),
                        isPlaying = isPlaying,
                        positionMs = positionMs,
                        durationMs = durationMs,
                        onTogglePlay = {
                            try {
                                if (isPlaying) { playerController.pause(); isPlaying = false }
                                else { playerController.play(track); isPlaying = true }
                            } catch (ex: Exception) { Log.e(TAG, "toggle failed", ex) }
                        },
                        onSeek = { ms -> playerController.seekTo(ms) },
                        onBack = { musicViewModel.clearTrack(); showPlayerFor = null }
                    )
                }
            }
        }

        // handle incoming OAuth redirect if any
        handleIntentIfAuth(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntentIfAuth(intent)
    }

    private fun openCustomTab(url: String) {
        try {
            val uri = Uri.parse(url)
            val customTabsIntent = androidx.browser.customtabs.CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(this, uri)
        } catch (ex: Exception) {
            try { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) } catch (e: Exception) { Toast.makeText(this, "Cannot open browser", Toast.LENGTH_LONG).show() }
        }
    }

    private fun handleIntentIfAuth(intent: Intent?) {
        try {
            val data = intent?.data
            if (data?.scheme == "kmp-audio-app" && data.host == "oauth") {
                val code = data.getQueryParameter("code")
                code?.let {
                    lifecycleScope.launch {
                        // exchange code for token using httpClient (Ktor)
                        try {
                            // demo: not performing request — just set AuthState
                            AuthState.setToken("DEMO_TOKEN")
                        } catch (ex: Exception) {
                            Log.e(TAG, "token exchange failed", ex)
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "handleIntentIfAuth failed", ex)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try { playerController.release() } catch (_: Exception) {}
    }
}
