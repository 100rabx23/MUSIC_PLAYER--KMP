package com.example.milewebassignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.milewebassignment.models.Track

@Composable
fun PlayerScreen(
    track: Track,
    isPlaying: Boolean,
    positionMs: Long,
    durationMs: Long,
    onTogglePlay: () -> Unit,
    onSeek: (Long) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(track.title) }, navigationIcon = { TextButton(onClick = onBack) { Text("Back") } })
    }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(18.dp))
            AsyncImage(model = track.artUrl, contentDescription = null, modifier = Modifier.size(260.dp))
            Spacer(Modifier.height(16.dp))
            Text(track.title, style = MaterialTheme.typography.titleLarge)
            Text(track.artist, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(16.dp))

            // simple progress
            val progress = if (durationMs > 0) positionMs.toFloat() / durationMs.toFloat() else 0f
            Slider(value = progress.coerceIn(0f,1f), onValueChange = {
                val pos = (it * durationMs).toLong()
                onSeek(pos)
            }, modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(24.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedButton(onClick = { /* prev */ }) { Text("⏮") }
                Button(onClick = onTogglePlay) { Text(if (isPlaying) "Pause" else "Play") }
                OutlinedButton(onClick = { /* next */ }) { Text("⏭") }
            }
        }
    }
}
