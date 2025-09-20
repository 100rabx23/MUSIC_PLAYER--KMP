package com.example.milewebassignment.auth

/**
 * Simple in-memory auth state. Replace backing store with DataStore or EncryptedSharedPreferences for production.
 */
object AuthState {
    @Volatile
    private var accessToken: String? = null

    fun setAccessToken(token: String) {
        accessToken = token
    }

    fun getAccessToken(): String? = accessToken

    fun hasToken(): Boolean = !accessToken.isNullOrEmpty()

    fun clearToken() { accessToken = null }
}
