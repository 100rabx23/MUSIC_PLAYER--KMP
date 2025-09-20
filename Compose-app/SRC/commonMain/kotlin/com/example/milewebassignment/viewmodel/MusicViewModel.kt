package com.example.milewebassignment.viewmodel

import com.example.milewebassignment.models.Playlist
import com.example.milewebassignment.models.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicViewModel {
    private val _playlists = MutableStateFlow<List<Playlist>>(samplePlaylists())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack.asStateFlow()

    fun selectTrack(track: Track) {
        _currentTrack.value = track
    }

    fun clearTrack() { _currentTrack.value = null }

    companion object {
        private fun samplePlaylists(): List<Playlist> {
            val tracks = (1..8).map {
                Track(
                    id = "t$it",
                    title = "Sample Song $it",
                    artist = "Artist $it",
                    artUrl = "https://picsum.photos/seed/album$it/400/400",
                    streamUrl = if (it % 2 == 0) "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3" else "https://www2.cs.uic.edu/~i101/SoundFiles/BabyElephantWalk60.wav"
                )
            }
            return listOf(
                Playlist("p1", "Top Charts", "https://picsum.photos/seed/pl1/400/400", tracks.subList(0,4)),
                Playlist("p2", "Chill", "https://picsum.photos/seed/pl2/400/400", tracks.subList(4,8))
            )
        }
    }
}
