package com.example.android.hello_compose.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.hello_compose.R
import com.example.android.common.theme.Amber600
import com.example.android.common.theme.Green300
import com.example.android.common.theme.Green800
import com.example.android.common.theme.Purple100
import com.example.android.common.theme.Purple700
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private enum class TabPage {
  Home,
  Work
}

@Composable
fun Home() {
  // String resources.
  val allTasks = stringArrayResource(R.array.tasks)
  val allTopics = stringArrayResource(R.array.topics).toList()

  // The currently selected tab.
  var tabPage by remember { mutableStateOf(TabPage.Home) }

  // True if the whether data is currently loading.
  var weatherLoading by remember { mutableStateOf(false) }

  // Holds all the tasks currently shown on the task list.
  val tasks = remember { mutableStateListOf(*allTasks) }

  // Holds the topic that is currently expanded to show its body.
  var expandedTopic by remember { mutableStateOf<String?>(null) }

  // True if the message about the edit feature is shown.
  var editMessageShown by remember { mutableStateOf(false) }

  // Simulates loading weather data. This takes 3 seconds.
  suspend fun loadWeather() {
    if (!weatherLoading) {
      weatherLoading = true
      delay(3000L)
      weatherLoading = false
    }
  }

  // Shows the message about edit feature.
  suspend fun showEditMessage() {
    if (!editMessageShown) {
      editMessageShown = true
      delay(3000L)
      editMessageShown = false
    }
  }

  // Load the weather at the initial composition.
  LaunchedEffect(Unit) {
    loadWeather()
  }

  val lazyListState = rememberLazyListState()

  // The background color. The value is changed by the current tab.
  // TODO 1: Animate this color change.
  val backgroundColor by animateColorAsState(
    if (tabPage == TabPage.Home) Purple100 else Green300
  )

  // The coroutine scope for event handlers calling suspend functions.
  val coroutineScope = rememberCoroutineScope()
  Scaffold(
    topBar = {
      HomeTabBar(
        backgroundColor = backgroundColor,
        tabPage = tabPage,
        onTabSelected = { tabPage = it }
      )
    },
    backgroundColor = backgroundColor,
    floatingActionButton = {
      HomeFloatingActionButton(
        extended = lazyListState.isScrollingUp(),
        onClick = {
          coroutineScope.launch {
            showEditMessage()
          }
        }
      )
    }
  ) {
    LazyColumn(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
      state = lazyListState
    ) {
      // Weather
      item { Header(title = stringResource(R.string.weather)) }
      item { Spacer(modifier = Modifier.height(16.dp)) }
      item {
        Surface(
          modifier = Modifier.fillMaxWidth(),
          elevation = 2.dp
        ) {
          if (weatherLoading) {
            LoadingRow()
          } else {
            WeatherRow(onRefresh = {
              coroutineScope.launch {
                loadWeather()
              }
            })
          }
        }
      }
      item { Spacer(modifier = Modifier.height(32.dp)) }

      // Topics
      item { Header(title = stringResource(R.string.topics)) }
      item { Spacer(modifier = Modifier.height(16.dp)) }
      items(allTopics) { topic ->
        TopicRow(
          topic = topic,
          expanded = expandedTopic == topic,
          onClick = {
            expandedTopic = if (expandedTopic == topic) null else topic
          }
        )
      }
      item { Spacer(modifier = Modifier.height(32.dp)) }

      // Tasks
      item { Header(title = stringResource(R.string.tasks)) }
      item { Spacer(modifier = Modifier.height(16.dp)) }
      if (tasks.isEmpty()) {
        item {
          TextButton(onClick = { tasks.clear(); tasks.addAll(allTasks) }) {
            Text(stringResource(R.string.add_tasks))
          }
        }
      }
      items(count = tasks.size) { i ->
        val task = tasks.getOrNull(i)
        if (task != null) {
          key(task) {
            TaskRow(
              task = task,
              onRemove = { tasks.remove(task) }
            )
          }
        }
      }
    }
    EditMessage(editMessageShown)
  }
}

// AnimatedVisibility is currently an experimental API in Compose Animation.
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HomeFloatingActionButton(
  extended: Boolean,
  onClick: () -> Unit
) {
  // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
  // how it should animate.
  FloatingActionButton(onClick = onClick) {
    Row(
      modifier = Modifier.padding(horizontal = 16.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = null
      )
      // Toggle the visibility of the content with animation.
      AnimatedVisibility(extended) {
        Text(
          text = stringResource(R.string.edit),
          modifier = Modifier
            .padding(start = 8.dp, top = 3.dp)
        )
      }
    }
  }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun EditMessage(shown: Boolean) {
  AnimatedVisibility(
    visible = shown,
    enter = slideInVertically(
      // Enters by sliding down from offset -fullHeight to 0.
      initialOffsetY = { fullHeight -> -fullHeight },
      animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing),
    ),
    exit = slideOutVertically(
      // Exits by sliding up from offset 0 to -fullHeight.
      targetOffsetY = { fullHeight -> -fullHeight },
      animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
    )
  ) {
    Surface(
      modifier = Modifier.fillMaxWidth(),
      color = MaterialTheme.colors.secondary,
      elevation = 4.dp
    ) {
      Text(
        text = stringResource(R.string.edit_message),
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
  var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
  var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
  return remember(this) {
    derivedStateOf {
      if (previousIndex != firstVisibleItemIndex) {
        previousIndex > firstVisibleItemIndex
      } else {
        previousScrollOffset >= firstVisibleItemScrollOffset
      }.also {
        previousIndex = firstVisibleItemIndex
        previousScrollOffset = firstVisibleItemScrollOffset
      }
    }
  }.value
}

@Composable
private fun Header(
  title: String
) {
  Text(
    text = title,
    modifier = Modifier.semantics { heading() },
    style = MaterialTheme.typography.h5
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TopicRow(topic: String, expanded: Boolean, onClick: () -> Unit) {
  TopicRowSpacer(visible = expanded)
  Surface(
    modifier = Modifier
      .fillMaxWidth(),
    elevation = 2.dp,
    onClick = onClick
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .animateContentSize()
    ) {
      Row {
        Icon(
          imageVector = Icons.Default.Info,
          contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          text = topic,
          style = MaterialTheme.typography.body1
        )
      }
      if (expanded) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = stringResource(R.string.lorem_ipsum),
          textAlign = TextAlign.Justify
        )
      }
    }
  }
  TopicRowSpacer(visible = expanded)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopicRowSpacer(visible: Boolean) {
  AnimatedVisibility(visible = visible) {
    Spacer(modifier = Modifier.height(8.dp))
  }
}

@Composable
private fun HomeTabBar(
  backgroundColor: Color,
  tabPage: TabPage,
  onTabSelected: (tabPage: TabPage) -> Unit,
) {
  TabRow(
    selectedTabIndex = tabPage.ordinal,
    backgroundColor = backgroundColor,
    indicator = { tabPositions ->
      HomeTabIndicator(tabPositions, tabPage)
    }
  ) {
    HomeTab(
      icon = Icons.Default.Home,
      title = stringResource(R.string.home),
      onClick = { onTabSelected(TabPage.Home) }
    )

    HomeTab(
      icon = Icons.Default.AccountBox,
      title = stringResource(R.string.work),
      onClick = { onTabSelected(TabPage.Work) }
    )
  }
}

@Composable
private fun HomeTabIndicator(
  tabPositions: List<TabPosition>,
  tabPage: TabPage
) {
  val transition = updateTransition(tabPage, label = "Tab indicator")
  val indicatorLeft by transition.animateDp(
    transitionSpec = {
      if (TabPage.Home isTransitioningTo TabPage.Work) {
        // Indicator moves to the right.
        // The left edge moves slower than the right edge.
        spring(stiffness = Spring.StiffnessVeryLow)
      } else {
        // Indicator moves to the left.
        // The left edge moves faster than the right edge.
        spring(stiffness = Spring.StiffnessMedium)
      }
    },
    label = "Indicator left",
  ) { page ->
    tabPositions[page.ordinal].left
  }
  val indicatorRight by transition.animateDp(
    transitionSpec = {
      if (TabPage.Home isTransitioningTo TabPage.Work) {
        // Indicator moves to the right
        // The right edge moves faster than the left edge.
        spring(stiffness = Spring.StiffnessMedium)
      } else {
        // Indicator moves to the left.
        // The right edge moves slower than the left edge.
        spring(stiffness = Spring.StiffnessVeryLow)
      }
    },
    label = "Indicator right"
  ) { page ->
    tabPositions[page.ordinal].right
  }
  val color by transition.animateColor(
    label = "Border color"
  ) { page ->
    if (page == TabPage.Home) Purple700 else Green800
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .wrapContentSize(align = Alignment.BottomStart)
      .offset(x = indicatorLeft)
      .width(indicatorRight - indicatorLeft)
      .padding(4.dp)
      .fillMaxSize()
      .border(
        BorderStroke(2.dp, color),
        RoundedCornerShape(4.dp)
      )
  )
}

@Composable
private fun HomeTab(
  icon: ImageVector,
  title: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .clickable(onClick = onClick)
      .padding(16.dp),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null
    )
    Spacer(Modifier.width(16.dp))
    Text(text = title)
  }
}

@Composable
private fun WeatherRow(
  onRefresh: () -> Unit
) {
  Row(
    modifier = Modifier
      .heightIn(min = 64.dp)
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(48.dp)
        .clip(CircleShape)
        .background(Amber600)
    )
    Spacer(Modifier.width(16.dp))
    Text(
      text = stringResource(R.string.temperature),
      fontSize = 24.sp
    )
    Spacer(Modifier.weight(1f))
    IconButton(onClick = onRefresh) {
      Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = stringResource(R.string.refresh)
      )
    }
  }
}

@Composable
private fun LoadingRow() {
  val infiniteAnimation = rememberInfiniteTransition()
  val alpha by infiniteAnimation.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = keyframes {
        durationMillis = 1000
        0.7f at 500
      },
      repeatMode = Reverse,
    )
  )
  Row(
    modifier = Modifier
      .heightIn(min = 64.dp)
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(48.dp)
        .clip(CircleShape)
        .background(Color.LightGray.copy(alpha = alpha))
    )
    Spacer(Modifier.width(16.dp))
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(32.dp)
        .background(Color.LightGray.copy(alpha = alpha))
    )
  }
}

@Composable
private fun TaskRow(task: String, onRemove: () -> Unit = {}) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .swipeToDismiss(onRemove),
    elevation = 2.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Check,
        contentDescription = null,
      )
      Spacer(Modifier.width(16.dp))
      Text(
        text = task,
        style = MaterialTheme.typography.body1
      )
    }
  }
}

private fun Modifier.swipeToDismiss(
  onDismissed: () -> Unit
): Modifier = composed {
  // Create an Animatable instance for the offset of the swiped element.
  val offsetX = remember { Animatable(0f) }
  pointerInput(Unit) {
    // Used to calculate a settling position of a fling animation.
    val decay = splineBasedDecay<Float>(this)
    // Wrap in a coroutine scope to use suspend functions for touch events and animation.
    coroutineScope {
      while (true) {
        // Wait for a touch down event.
        val pointerId = awaitPointerEventScope { awaitFirstDown().id }
        // Touch detected; the animation should be stopped.
        offsetX.stop()
        // Prepare for drag events and record velocity of a fling.
        val velocityTracker = VelocityTracker()
        // Wait for drag events.
        awaitPointerEventScope {
          horizontalDrag(pointerId) { change ->
            // Apply the drag change to the Animatable offset.
            val horizontalDragOffset = offsetX.value + change.positionChange().x
            launch {
              offsetX.snapTo(horizontalDragOffset)
            }
            // Record the velocity of the drag.
            velocityTracker.addPosition(change.uptimeMillis, change.position)
            // Consume the gesture event, not passed to external
            change.consumePositionChange()
          }
        }

        // Dragging finished. Calculate the velocity of the fling.
        val velocity = velocityTracker.calculateVelocity().x
        // Calculate the eventual position where the fling should settle
        // based on the current offset value and velocity
        val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
        // Set the upper and lower bounds so that the animation stops when it
        // reaches the edge.
        offsetX.updateBounds(
          lowerBound = -size.width.toFloat(),
          upperBound = size.width.toFloat()
        )

        launch {
          if (targetOffsetX.absoluteValue <= size.width) {
            // Not enough velocity; Slide back.
            offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
          } else {
            // Enough velocity to slide away the element to the edge.
            offsetX.animateDecay(velocity, decay)
            // The element was swiped away.
            onDismissed()
          }
        }
      }
    }
  }
    .offset {
      IntOffset(offsetX.value.roundToInt(), 0)
    }
}

@Preview
@Composable
private fun PreviewHomeTabBar() {
  HomeTabBar(
    backgroundColor = Purple100,
    tabPage = TabPage.Home,
    onTabSelected = {}
  )
}

@Preview
@Composable
private fun PreviewHome() {
  AnimationCodelabTheme {
    Home()
  }
}