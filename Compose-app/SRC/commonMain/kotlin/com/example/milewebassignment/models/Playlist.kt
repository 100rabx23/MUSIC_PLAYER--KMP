package com.example.milewebassignment.models

data class Playlist(
    val id: String,
    val title: String,
    val coverUrl: String,
    val tracks: List<Track>
)
