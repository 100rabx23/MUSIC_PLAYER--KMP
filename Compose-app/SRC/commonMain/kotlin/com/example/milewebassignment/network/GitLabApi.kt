package com.example.milewebassignment.network


import com.example.milewebassignment.auth.UserProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class GitLabApi(private val httpClient: HttpClient, private val accessToken: String) {

    suspend fun getUserProfile(): UserProfile {
        val response: HttpResponse = httpClient.get("https://gitlab.com/api/v4/user") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }
        return response.body()
    }
}
