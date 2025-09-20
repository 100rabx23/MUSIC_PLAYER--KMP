package com.example.milewebassignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.milewebassignment.viewmodel.AuthViewModel

@Composable
fun LoginScreen(authViewModel: AuthViewModel, onOpenOAuth: (String) -> Unit = {}) {
    // This screen is largely visual in this demo. For full OAuth, Android Activity handles opening custom tab.
    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary)))){
        Card(modifier = Modifier.align(Alignment.Center).padding(24.dp), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Welcome to MileWeb", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(10.dp))
                Text("Sign in to enjoy music", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(24.dp))
                Button(onClick = { authViewModel.loginMock() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Continue (Mock login)")
                }
            }
        }
    }
}
