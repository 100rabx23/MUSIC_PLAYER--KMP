package com.example.milewebassignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.milewebassignment.models.Playlist
import com.example.milewebassignment.viewmodel.AuthViewModel
import com.example.milewebassignment.viewmodel.MusicViewModel

@Composable
fun DashboardScreen(authViewModel: AuthViewModel, musicViewModel: MusicViewModel, onPlayTrack: (com.example.milewebassignment.models.Track) -> Unit) {
    val playlists by musicViewModel.playlists.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Discover") }, actions = {
            IconButton(onClick = { authViewModel.logout() }) { Icon(Icons.Default.Logout, contentDescription = "logout") }
        })
    }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                playlists.forEach { pl ->
                    item {
                        Text(pl.title, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.titleMedium)
                    }
                    items(pl.tracks) { track ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { musicViewModel.selectTrack(track); onPlayTrack(track) }
                            .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(model = track.artUrl, contentDescription = null, modifier = Modifier.size(64.dp))
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(track.title, style = MaterialTheme.typography.titleMedium)
                                Text(track.artist, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Divider()
                    }
                }
            }
        }
    }
}
