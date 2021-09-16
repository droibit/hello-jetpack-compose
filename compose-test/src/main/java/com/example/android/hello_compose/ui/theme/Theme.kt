package com.example.android.hello_compose.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.android.common.theme.DarkBlue900
import com.example.android.common.theme.Green500

private val ColorPalette = darkColors(
  primary = Green500,
  surface = DarkBlue900,
  onSurface = Color.White,
  background = DarkBlue900,
  onBackground = Color.White
)

@Composable
fun RallyTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colors = ColorPalette,
    typography = Typography,
    content = content
  )
}

/**
 * A theme overlay used for dialogs.
 */
@Composable
fun RallyDialogThemeOverlay(content: @Composable () -> Unit) {
  val dialogColors = darkColors(
    primary = Color.White,
    surface = Color.White.copy(alpha = 0.12f).compositeOver(Color.Black),
    onSurface = Color.White
  )

  // Copy the current [Typography] and replace some text styles for this theme.
  val currentTypography = MaterialTheme.typography
  val dialogTypography = currentTypography.copy(
    body2 = currentTypography.body1.copy(
      fontWeight = FontWeight.Normal,
      fontSize = 20.sp,
      lineHeight = 28.sp,
      letterSpacing = 1.sp
    ),
    button = currentTypography.button.copy(
      fontWeight = FontWeight.Bold,
      letterSpacing = 0.2.em,
    ),
  )
  MaterialTheme(
    colors = dialogColors,
    typography = dialogTypography,
    content = content
  )
}