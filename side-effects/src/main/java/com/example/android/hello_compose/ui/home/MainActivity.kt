package com.example.android.hello_compose.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.example.android.hello_compose.ui.details.launchDetailsActivity
import com.example.android.hello_compose.ui.theme.CraneTheme
import com.example.android.hello_compose.util.ProvideImageLoader
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      ProvideWindowInsets {
        ProvideImageLoader {
          CraneTheme {
            MainScreen(onExploreItemClicked = {
              launchDetailsActivity(context = this, item = it)
            })
          }
        }
      }
    }
  }
}

@Composable
private fun MainScreen(onExploreItemClicked: OnExploreItemClicked) {
  Surface(color = MaterialTheme.colors.primary) {
    var showLandingScreen by remember { mutableStateOf(true) }
    if (showLandingScreen) {
      LandingScreen { showLandingScreen = false }
    } else  {
      CraneHome(onExploreItemClicked = onExploreItemClicked)
    }
  }
}