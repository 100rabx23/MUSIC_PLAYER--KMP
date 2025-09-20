package com.example.milewebassignment.auth

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "LoginScreen"

const val CLIENT_ID = "0466b259b523fcad3c12dc61c6af6f3983b5b26c6174d1cd9b4cdf81a35cba6c"
const val CLIENT_SECRET = "gloas-d176c4dc72cbd096fa8bf87d03f71373b8da4960c1f57ae8787072719a82b880"
const val REDIRECT_URI = "kmp-audio-app://oauth/callback"
const val SCOPE = "read_user"
const val AUTH_URL = "https://gitlab.com/oauth/authorize"

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {  // ✅ parameter now available
    val context = LocalContext.current
    val activity = context as? Activity
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E3C72), Color(0xFF2A5298)) // Modern gradient
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "🎧 KMP Audio Player",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Sign in with GitLab to unlock your personalized music experience",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isLoading) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Redirecting to GitLab...")
                } else {
                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                isLoading = true
                                error = null
                                try {
                                    val uri = Uri.parse(
                                        "$AUTH_URL?client_id=$CLIENT_ID" +
                                                "&redirect_uri=$REDIRECT_URI" +
                                                "&response_type=code&scope=$SCOPE"
                                    )
                                    val customTabsIntent = CustomTabsIntent.Builder().build()

                                    if (activity != null) {
                                        customTabsIntent.launchUrl(activity, uri)
                                        onLoginSuccess() // ✅ Trigger callback (optional)
                                    } else {
                                        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                    }
                                } catch (ex: ActivityNotFoundException) {
                                    Log.e(TAG, "No browser found for login", ex)
                                    error = "No browser found on device."
                                    isLoading = false
                                } catch (ex: Exception) {
                                    Log.e(TAG, "Failed to launch login", ex)
                                    error = "Failed to start login: ${ex.localizedMessage}"
                                    isLoading = false
                                }
                            }
                        },
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🔑 Login with GitLab")
                    }
                }

                error?.let {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
