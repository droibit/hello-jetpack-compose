package com.example.android.hello_compose.state

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIconRow(
  icon: TodoIcon,
  onIconChange: (TodoIcon) -> Unit,
  modifier: Modifier = Modifier,
  visible: Boolean = true,
) {
  // remember these specs so they don't restart if recomposing during the animation
  // this is required since TweenSpec restarts on interruption
  val enter = remember { fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing)) }
  val exit = remember { fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing)) }
  Box(modifier.defaultMinSize(minHeight = 16.dp)) {
    AnimatedVisibility(
      visible = visible,
      enter = enter,
      exit = exit
    ) {
      IconRow(icon, onIconChange)
    }
  }
}

@Composable
fun IconRow(
  icon: TodoIcon,
  onIconChange: (TodoIcon) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(modifier) {
    for (todoIcon in TodoIcon.values()) {
      SelectableIconButton(
        icon = todoIcon.imageVector,
        iconContentDescription = todoIcon.contentDescription,
        onIconSelected = { onIconChange(todoIcon) },
        isSelected = todoIcon == icon
      )
    }
  }
}

@Composable
fun SelectableIconButton(
  icon: ImageVector,
  @StringRes iconContentDescription: Int,
  onIconSelected: () -> Unit,
  isSelected: Boolean,
  modifier: Modifier = Modifier
) {
  val tint = if (isSelected) {
    MaterialTheme.colors.primary
  } else {
    MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
  }

  TextButton(
    onClick = onIconSelected,
    shape = CircleShape,
    modifier = modifier
  ) {
    Column {
      Icon(
        imageVector = icon,
        tint = tint,
        contentDescription = stringResource(id = iconContentDescription)
      )

      if (isSelected) {
        Box(
          Modifier
            .padding(top = 3.dp)
            .width(icon.defaultWidth)
            .height(1.dp)
            .background(tint)
        )
      } else {
        Spacer(modifier = Modifier.height(4.dp))
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewIconRow() = IconRow(icon = TodoIcon.Square, onIconChange = {})

@Composable
fun TodoItemInputBackground(
  elevate: Boolean,
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit,
) {
  val animatedElevation by animateDpAsState(
    if (elevate) 1.dp else 0.dp, TweenSpec(500)
  )
  Surface(
    color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
    elevation = animatedElevation,
    shape = RectangleShape,
  ) {
    Row(
      modifier = modifier.animateContentSize(animationSpec = TweenSpec(300)),
      content = content
    )
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TodoInputText(
  text: String,
  onTextChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  onImeAction: () -> Unit = {},
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  TextField(
    value = text,
    onValueChange = onTextChange,
    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
    maxLines = 1,
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    keyboardActions = KeyboardActions(
      onDone = {
        onImeAction()
        keyboardController?.hide()
      }
    ),
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoInputText() {
  TodoInputText(
    text = "Todo",
    onTextChange = {},
  )
}

@Composable
fun TodoEditButton(
  onClick: () -> Unit,
  text: String,
  modifier: Modifier = Modifier,
  enabled: Boolean = true
) {
  Button(
    onClick = onClick,
    shape = CircleShape,
    enabled = enabled,
    modifier = modifier
  ) {
    Text(text)
  }
}

@Preview
@Composable
fun TodoEditButtonPreview() {
  TodoEditButton(
    onClick = {},
    text = "Edit"
  )
}