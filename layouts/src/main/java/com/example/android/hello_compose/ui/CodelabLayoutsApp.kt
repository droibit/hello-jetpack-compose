package com.example.android.hello_compose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.common.theme.HelloComposeTheme

@Composable
fun CodelabLayoutsApp() {
  HelloComposeTheme {
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Text(text = "LayoutsCodelab")
          },
          actions = {
            IconButton(onClick = { /* doSomething() */ }) {
              Icon(Icons.Filled.Favorite, contentDescription = null)
            }
          }
        )
      }
    ) { innerPadding ->
      LayoutsBodyContent(Modifier.padding(innerPadding))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CodelabLayoutsAppPreview() {
  CodelabLayoutsApp()
}