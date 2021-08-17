package com.example.android.hello_compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun LayoutsBodyContent(modifier: Modifier = Modifier) {
  Column(modifier = modifier.fillMaxHeight()) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val listSize = 100
    LayoutsImageList(
      listSize,
      state = scrollState,
      modifier = Modifier.weight(1f)
    )
    Row(modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)) {
      Button(
        modifier = Modifier.weight(1f),
        onClick = {
          coroutineScope.launch {
            scrollState.animateScrollToItem(0)
          }
        }
      ) {
        Text("↑")
      }

      Spacer(modifier = Modifier.width(8.dp))

      Button(
        modifier = Modifier.weight(1f),
        onClick = {
          coroutineScope.launch {
            scrollState.animateScrollToItem(listSize - 1)
          }
        }
      ) {
        Text("↓")
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun LayoutsBodyContentPreview() {
  LayoutsBodyContent()
}