package com.example.android.hello_compose.animation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.android.hello_compose.ui.theme.Purple500
import com.example.android.hello_compose.ui.theme.Purple700
import com.example.android.hello_compose.ui.theme.Teal200

@Composable
fun AnimationCodelabTheme(content: @Composable () -> Unit) {
  val colors = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
  )
  MaterialTheme(
    colors = colors,
    content = content
  )
}