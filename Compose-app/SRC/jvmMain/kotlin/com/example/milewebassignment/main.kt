package com.example.milewebassignment
import
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.milewebassignment.ui.theme.AppTheme
import com.example.milewebassignment.viewmodel.AuthViewModel
import com.example.milewebassignment.viewmodel.MusicViewModel

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "MileWeb Music") {
        AppTheme {
            val auth = AuthViewModel()
            val music = MusicViewModel()
            // Reuse shared AppNavigator-like root if created — for brevity call Dashboard directly
            // Desktop: UI only, playback unsupported in this stub.
        }
    }
}
