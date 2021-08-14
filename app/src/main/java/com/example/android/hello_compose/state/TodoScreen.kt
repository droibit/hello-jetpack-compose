package com.example.android.hello_compose.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.random.Random

@Composable
fun TodoActivityScreen(todoViewModel: TodoViewModel = viewModel()) {
  TodoScreen(
    items = todoViewModel.todoItems,
    currentlyEditing = todoViewModel.currentEditItem,
    onAddItem = todoViewModel::addItem,
    onRemoveItem = todoViewModel::removeItem,
    onStartEdit = todoViewModel::onEditItemSelected,
    onEditItemChange = todoViewModel::onEditItemChange,
    onEditDone = todoViewModel::onEditDone,
  )
}

@Composable
fun TodoScreen(
  items: List<TodoItem>,
  currentlyEditing: TodoItem?,
  onAddItem: (TodoItem) -> Unit,
  onRemoveItem: (TodoItem) -> Unit,
  onStartEdit: (TodoItem) -> Unit,
  onEditItemChange: (TodoItem) -> Unit,
  onEditDone: () -> Unit,
) {
  val enableTopSection = currentlyEditing == null
  Column {
    TodoItemInputBackground(elevate = enableTopSection) {
      if (enableTopSection) {
        TodoItemEntryInput(onItemComplete = onAddItem)
      } else {
        Text(
          text = "Editing item",
          style = MaterialTheme.typography.h6,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(16.dp)
            .fillMaxWidth()
        )
      }
    }

    LazyColumn(
      modifier = Modifier.weight(1f),
      contentPadding = PaddingValues(top = 8.dp)
    ) {
      items(items = items) { todo ->
        if (currentlyEditing?.id == todo.id) {
          TodoItemInlineEditor(
            item = currentlyEditing,
            onEditItemChange = onEditItemChange,
            onEditDone = onEditDone,
            onRemoveItem = { onRemoveItem(todo) }
          )
        } else {
          TodoRow(
            todo = todo,
            onItemClicked = { onStartEdit(it) },
            modifier = Modifier.fillParentMaxWidth()
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoScreen() {
  val items = listOf(
    TodoItem("Learn compose", TodoIcon.Event),
    TodoItem("Take the codelab"),
    TodoItem("Apply state", TodoIcon.Done),
    TodoItem("Build dynamic UIs", TodoIcon.Square)
  )
  TodoScreen(
    items,
    currentlyEditing = null,
    onAddItem = {},
    onRemoveItem = {},
    onStartEdit = {},
    onEditItemChange = {},
    onEditDone = {}
  )
}

@Composable
fun TodoRow(
  todo: TodoItem,
  onItemClicked: (TodoItem) -> Unit,
  modifier: Modifier = Modifier,
  iconAlpha: Float = remember(todo.id) { randomTint() }
) {
  Row(
    modifier = modifier
      .clickable { onItemClicked(todo) }
      .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(todo.task)

    Icon(
      imageVector = todo.icon.imageVector,
      tint = LocalContentColor.current.copy(alpha = iconAlpha),
      contentDescription = stringResource(id = todo.icon.contentDescription)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoRow() {
  val todo = remember { generateRandomTodoItem() }
  TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}

private fun randomTint(): Float {
  return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
  val (text, setText) = remember { mutableStateOf("") }
  val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
  val submit = {
    if (text.isNotBlank()) {
      onItemComplete(TodoItem(text, icon))
      setIcon(TodoIcon.Default)
      setText("")
    }
  }
  TodoItemInput(
    text = text,
    onTextChange = setText,
    icon = icon,
    onIconChange = setIcon,
    submit = submit,
    iconsVisible = text.isNotBlank()
  ) {
    TodoEditButton(
      onClick = submit,
      text = "Add",
      enabled = text.isNotBlank()
    )
  }
}

@Composable
fun TodoItemInput(
  text: String,
  onTextChange: (String) -> Unit,
  icon: TodoIcon,
  onIconChange: (TodoIcon) -> Unit,
  submit: () -> Unit,
  iconsVisible: Boolean,
  buttonSlot: @Composable () -> Unit,
) {
  Column {
    Row(
      Modifier
        .padding(horizontal = 16.dp)
        .padding(top = 16.dp)
    ) {
      TodoInputText(
        text = text,
        onTextChange = onTextChange,
        modifier = Modifier
          .weight(1f)
          .padding(end = 8.dp),
        onImeAction = submit
      )

      Spacer(modifier = Modifier.width(8.dp))
      Box(Modifier.align(Alignment.CenterVertically)) {
        buttonSlot()
      }
    }

    if (iconsVisible) {
      AnimatedIconRow(icon, onIconChange, Modifier.padding(top = 8.dp))
    } else {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
fun TodoItemInlineEditor(
  item: TodoItem,
  onEditItemChange: (TodoItem) -> Unit,
  onEditDone: () -> Unit,
  onRemoveItem: () -> Unit,
) = TodoItemInput(
  text = item.task,
  onTextChange = { onEditItemChange(item.copy(task = it)) },
  icon = item.icon,
  onIconChange = { onEditItemChange(item.copy(icon = it)) },
  submit = onEditDone,
  iconsVisible = true
) {
  Row {
    val shrinkButtons = Modifier.widthIn(20.dp)
    TextButton(onClick = onEditDone, modifier = shrinkButtons) {
      Text(
        text = "\uD83D\uDCBE", // floppy disk
        textAlign = TextAlign.End,
        modifier = Modifier.width(30.dp)
      )
    }

    TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
      Text(
        text = "❌",
        textAlign = TextAlign.End,
        modifier = Modifier.width(30.dp)
      )
    }
  }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTodoItemInput() = TodoItemInput(onItemComplete = { })
