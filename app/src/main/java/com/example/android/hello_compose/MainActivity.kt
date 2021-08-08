package com.example.android.hello_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.android.hello_compose.basics.BasicApp
import com.example.android.hello_compose.tutorial.Conversation
import com.example.android.hello_compose.tutorial.SampleData
import com.example.android.hello_compose.ui.theme.HelloComposeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      BasicApp()
    }
  }
}