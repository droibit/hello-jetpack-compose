package com.example.android.hello_compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun LayoutsSampleList() {
  val scrollState = rememberScrollState()
  Column(Modifier.verticalScroll(scrollState)) {
    repeat(100) {
      Text("Item #$it")
    }
  }
}

@Composable
fun LayoutsImageListItem(index: Int) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    Image(
      painter = rememberImagePainter(
        data = "https://developer.android.com/images/brand/Android_Robot.png"
      ),
      contentDescription = "Android Logo",
      modifier = Modifier.size(50.dp)
    )
    Spacer(Modifier.width(10.dp))
    Text("Item #$index", style = MaterialTheme.typography.subtitle1)
  }
}

@Composable
fun LayoutsImageList(listSize: Int, state: LazyListState, modifier: Modifier = Modifier) {
  LazyColumn(modifier = modifier, state = state) {
    items(listSize) {
      LayoutsImageListItem(index = it)
    }
  }
}