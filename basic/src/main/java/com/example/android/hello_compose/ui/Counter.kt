package com.example.android.hello_compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
  Button(
    onClick = { updateCount(count + 1) },
    colors = ButtonDefaults.buttonColors(
      backgroundColor = if (count > 5) Color.Green else Color.White
    )
  ) {
    Text("I've been clicked $count times")
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewCounter() {
  Column {
    Counter(count = 0) {}
    Counter(count = 6) {}
  }
}