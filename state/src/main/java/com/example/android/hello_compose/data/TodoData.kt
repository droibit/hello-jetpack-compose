package com.example.android.hello_compose.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.android.hello_compose.R.string
import com.example.android.hello_compose.data.TodoIcon.Companion
import java.util.UUID

data class TodoItem(
  val task: String,
  val icon: TodoIcon = Companion.Default,
  // since the user may generate identical tasks, give them each a unique ID
  val id: UUID = UUID.randomUUID()
)

enum class TodoIcon(val imageVector: ImageVector, @StringRes val contentDescription: Int) {
  Square(Icons.Default.CropSquare, string.cd_expand),
  Done(Icons.Default.Done, string.cd_done),
  Event(Icons.Default.Event, string.cd_event),
  Privacy(Icons.Default.PrivacyTip, string.cd_privacy),
  Trash(Icons.Default.RestoreFromTrash, string.cd_restore);

  companion object {
    val Default = Square
  }
}