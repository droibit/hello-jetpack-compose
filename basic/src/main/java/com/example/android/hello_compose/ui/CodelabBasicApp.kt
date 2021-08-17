package com.example.android.hello_compose.ui

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.common.theme.HelloComposeTheme

@Composable
fun CodelabBasicApp() {
  HelloComposeTheme {
    Surface(color = Color.Yellow) {
      BasicScreenContent()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewCodelabBasicApp() {
  CodelabBasicApp()
}