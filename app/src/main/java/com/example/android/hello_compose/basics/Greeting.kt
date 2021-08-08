package com.example.android.hello_compose.basics

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Greeting(name: String) {
  var isSelected by remember { mutableStateOf(false) }
  val backgroundColor by animateColorAsState(
    if (isSelected) Color.Red else Color.Transparent
  )

  Text(
    text = "Hello $name",
    style = MaterialTheme.typography.body1,
    modifier = Modifier
      .padding(24.dp)
      .background(backgroundColor)
      .clickable { isSelected = !isSelected }
  )
}

@Preview(showBackground = true)
@Composable
fun PreviewGreeting() {
  Greeting(name = "Android")
}
