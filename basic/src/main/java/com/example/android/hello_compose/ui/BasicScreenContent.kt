package com.example.android.hello_compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Divider
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
fun BasicScreenContent(names: List<String> = List(100) { "Hello Android #$it" }) {
  var counterState by remember { mutableStateOf(0) }

  Column(modifier = Modifier.fillMaxHeight()) {
    NameList(names, modifier = Modifier.weight(1f))
    Divider(color = Color.Transparent, thickness = 32.dp)
    Counter(count = counterState) { newCount ->
      counterState = newCount
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewBasicScreenContent() {
  BasicScreenContent()
}
