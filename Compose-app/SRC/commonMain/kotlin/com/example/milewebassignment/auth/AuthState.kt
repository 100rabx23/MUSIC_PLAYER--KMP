package com.example.milewebassignment.auth

object AuthState {
    private var token: String? = null

    fun setAccessToken(newToken: String) {
        token = newToken
    }

    fun getAccessToken(): String? = token

    fun hasToken(): Boolean = !token.isNullOrEmpty()
}
