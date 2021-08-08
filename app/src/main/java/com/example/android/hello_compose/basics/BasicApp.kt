package com.example.android.hello_compose.basics

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.hello_compose.ui.theme.HelloComposeTheme

@Composable
fun BasicApp() {
  HelloComposeTheme {
    Surface(color = Color.Yellow) {
      BasicScreenContent()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewBasicApp() {
  BasicApp()
}