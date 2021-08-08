package com.example.android.hello_compose.basics

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
  LazyColumn(modifier = modifier) {
    items(names) { name ->
      Greeting(name = name)
      Divider(color = Color.Black)
    }
  }
}