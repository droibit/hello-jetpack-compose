package com.example.android.hello_compose.tutorial

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.hello_compose.R.drawable

data class Message(
  val author: String,
  val body: String
)

@Composable
fun MessageCard(message: Message) {
  Row(
    modifier = Modifier.padding(all = 8.dp)
  ) {
    Image(
      painter = painterResource(drawable.ic_profile_picture),
      contentDescription = null,
      contentScale = ContentScale.Inside,
      colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
      modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)
        .background(Color.LightGray)
        .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
    )

    Spacer(modifier = Modifier.width(8.dp))

    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor: Color by animateColorAsState(
      if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )

    Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
      Text(
        text = message.author,
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.subtitle2
      )
      Spacer(modifier = Modifier.height(4.dp))

      Surface(
        shape = MaterialTheme.shapes.medium, elevation = 1.dp,
        color = surfaceColor,
        modifier = Modifier.animateContentSize().padding(all = 1.dp)
      ) {
        Text(
          text = message.body,
          modifier = Modifier.padding(all = 4.dp),
          style = MaterialTheme.typography.body2,
          maxLines = if (isExpanded) Int.MAX_VALUE else 1
        )
      }
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
fun PreviewMessageCard() {
  MessageCard(
    Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
  )
}
