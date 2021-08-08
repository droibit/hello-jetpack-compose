package com.example.android.hello_compose.tutorial

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.hello_compose.ui.theme.HelloComposeTheme

@Composable
fun Conversation(messages: List<Message>) {
  LazyColumn {
    items(messages) { message ->
      MessageCard(message)
    }
  }
}

@Preview(
  name = "Light Mode",
  showBackground = true
)
@Preview(
  "Dark Mode",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun PreviewConversation() {
  HelloComposeTheme {
    Conversation(messages = SampleData.conversationSample)
  }
}