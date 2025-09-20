package com.example.milewebassignment.network

object GitLabAuth {
    fun startLoginFlow(onSuccess: () -> Unit, onError: () -> Unit) {
        // For now, just simulate a successful login
        println("Simulating GitLab login...")
        onSuccess()
    }
}