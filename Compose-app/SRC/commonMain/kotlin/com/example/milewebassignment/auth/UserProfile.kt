package com.example.milewebassignment.auth



import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: Int,
    val username: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    val name: String? = null
)
