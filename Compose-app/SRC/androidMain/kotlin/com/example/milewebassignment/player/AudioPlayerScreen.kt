package com.example.milewebassignment.player

import android.content.Context
import com.example.milewebassignment.models.Track
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import android.util.Log

class AndroidPlayerController(private val context: Context) {
    private var exo: ExoPlayer? = null
    val duration: Long
        get() = exo?.duration ?: 0L
    val position: Long
        get() = exo?.currentPosition ?: 0L

    fun initIfNeeded() {
        if (exo == null) {
            exo = ExoPlayer.Builder(context).build()
        }
    }

    fun play(track: Track) {
        try {
            initIfNeeded()
            val item = MediaItem.fromUri(track.streamUrl)
            exo?.setMediaItem(item)
            exo?.prepare()
            exo?.play()
        } catch (ex: Exception) { Log.e("AndroidPlayer", "play failed", ex) }
    }

    fun pause() {
        try { exo?.pause() } catch (_: Exception) {}
    }

    fun stop() {
        try { exo?.stop() } catch (_: Exception) {}
    }

    fun seekTo(ms: Long) {
        try { exo?.seekTo(ms) } catch (_: Exception) {}
    }

    fun release() {
        try { exo?.release(); exo = null } catch (_: Exception) {}
    }
}
