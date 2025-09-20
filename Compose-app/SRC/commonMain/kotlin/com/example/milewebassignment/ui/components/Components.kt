package com.example.milewebassignment.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Avatar(url: String, sizeDp: Int = 40) {
    Surface(shape = CircleShape) {
        AsyncImage(model = url, contentDescription = "avatar", modifier = Modifier.size(sizeDp.dp))
    }
}
