package com.example.android.hello_compose.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.common.theme.HelloComposeTheme

@Composable
fun MyOwnColumn(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Layout(
    modifier = modifier,
    content = content,
  ) { measureables, constraints ->
    val placeables = measureables.map { it.measure(constraints) }

    var yPosition = 0
    layout(constraints.maxWidth, constraints.maxHeight) {
      placeables.forEach {
        it.placeRelative(x = 0, y = yPosition)
        yPosition += it.height
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MyOwnColumnPreview() {
  HelloComposeTheme {
    MyOwnColumn(modifier = Modifier.padding(8.dp)) {
      Text("MyOwnColumn")
      Text("places items")
      Text("vertically.")
      Text("We've done it by hand!")
    }
  }
}
