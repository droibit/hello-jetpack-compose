package com.example.android.hello_compose.theming

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.hello_compose.R
import com.example.android.hello_compose.theming.data.Post
import com.example.android.hello_compose.theming.data.PostRepo
import com.example.android.hello_compose.theming.theme.JetnewsTheme
import java.util.Locale

@Composable
fun Home() {
  val featured = remember { PostRepo.getFeaturedPost() }
  val posts = remember { PostRepo.getPosts() }
  JetnewsTheme {
    Scaffold(
      topBar = {
        AppBar()
      }
    ) { innerPadding ->
      LazyColumn(contentPadding = innerPadding) {
        item {
          Header(stringResource(R.string.top))
        }

        item {
          FeaturedPost(
            post = featured,
            modifier = Modifier.padding(16.dp)
          )
        }

        items(posts) { post ->
          PostItem(post)
          Divider(startIndent = 72.dp)
        }
      }
    }
  }
}

@Composable
private fun AppBar() {
  TopAppBar(
    navigationIcon = {
      Icon(
        imageVector = Icons.Rounded.Palette,
        contentDescription = null,
        modifier = Modifier.padding(horizontal = 16.dp)
      )
    },
    title = {
      Text(text = stringResource(R.string.app_title_jetnews))
    },
    backgroundColor = MaterialTheme.colors.primarySurface
  )
}

@Composable
fun Header(
  text: String,
  modifier: Modifier = Modifier
) {
  Surface(
    color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    contentColor = MaterialTheme.colors.primary,
    modifier = modifier.semantics { heading() }
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.subtitle2,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    )
  }
}

@Composable
fun FeaturedPost(
  post: Post,
  modifier: Modifier = Modifier
) {
  Card(modifier) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { }
    ) {
      Image(
        painter = painterResource(id = post.imageId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .heightIn(min = 180.dp)
          .fillMaxWidth()
      )
      Spacer(Modifier.height(16.dp))

      val padding = Modifier.padding(horizontal = 16.dp)
      Text(
        text = post.title,
        modifier = padding
      )
      Text(
        text = post.metadata.author.name,
        modifier = padding
      )

      PostMetadata(post, modifier = padding)
      Spacer(Modifier.height(16.dp))
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(
  post: Post,
  modifier: Modifier = Modifier
) {
  ListItem(
    modifier = modifier
      .clickable { }
      .padding(vertical = 8.dp),
    icon = {
      Image(
        painter = painterResource(post.imageThumbId),
        contentDescription = null,
        modifier = Modifier.clip(shape = MaterialTheme.shapes.small)
      )
    },
    text = {
      Text(
        text = post.title,
        modifier = modifier
      )
    },
    secondaryText = {
      PostMetadata(post, modifier)
    }
  )
}

@Composable
private fun PostMetadata(
  post: Post,
  modifier: Modifier = Modifier
) {
  val divider = "  •  "
  val tagDivider = "  "
  val text = buildAnnotatedString {
    append(post.metadata.date)
    append(divider)
    append(stringResource(R.string.read_time, post.metadata.readTimeMinutes))
    append(divider)

    val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
      background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
    )
    post.tags.forEachIndexed { index, tag ->
      if (index != 0) {
        append(tagDivider)
      }

      withStyle(tagStyle) {
        append(tag.uppercase(Locale.getDefault()))
      }
    }
  }

  CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
    Text(
      text = text,
      modifier = modifier
    )
  }
}

@Preview("Featured Post", showBackground = true)
@Composable
private fun FeaturedPostPreview() {
  val post = remember { PostRepo.getFeaturedPost() }
  JetnewsTheme {
    FeaturedPost(post = post)
  }
}

@Preview("Featured Post • Dark", showBackground = true)
@Composable
private fun FeaturedPostDarkPreview() {
  val post = remember { PostRepo.getFeaturedPost() }
  JetnewsTheme(darkTheme = true) {
    FeaturedPost(post = post)
  }
}

@Preview("Post Item", showBackground = true)
@Composable
private fun PostItemPreview() {
  val post = remember { PostRepo.getFeaturedPost() }
  Surface {
    PostItem(post = post)
  }
}

@Preview("Home")
@Composable
private fun HomePreview() {
  Home()
}

@Preview("Home • Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeDarkPreview() {
  Home()
}