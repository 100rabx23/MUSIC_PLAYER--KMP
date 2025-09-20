package com.example.milewebassignment

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform