package com.example.android.hello_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.android.hello_compose.ui.components.RallyTopAppBar
import com.example.android.hello_compose.ui.theme.RallyTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      RallyApp()
    }
  }
}

@Composable
fun RallyApp() {
  RallyTheme {
    val allScreens = RallyScreen.values().toList()
    var currentScreen by rememberSaveable { mutableStateOf(RallyScreen.Overview) }
    Scaffold(
      topBar = {
        RallyTopAppBar(
          allScreens = allScreens,
          onTabSelected = { screen -> currentScreen = screen },
          currentScreen = currentScreen
        )
      }
    ) { innerPadding ->
      Box(Modifier.padding(innerPadding)) {
        currentScreen.content(onScreenChange = { screen -> currentScreen = screen })
      }
    }
  }
}