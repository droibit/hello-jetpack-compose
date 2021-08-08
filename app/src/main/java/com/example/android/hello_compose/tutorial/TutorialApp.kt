package com.example.android.hello_compose.tutorial

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.android.hello_compose.ui.theme.HelloComposeTheme

@Composable
fun TutorialApp() {
  HelloComposeTheme {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
      Conversation(messages = SampleData.conversationSample)
    }
  }
}