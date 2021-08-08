package com.example.android.hello_compose.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.android.hello_compose.ui.theme.HelloComposeTheme

fun Modifier.firstBaselineToTop(
  firstBaselineToTop: Dp
) = this.then(
  layout { measureable, constraints ->
    val placeable = measureable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaselinePx = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaselinePx
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
      // Where the composable gets placed
      placeable.placeRelative(0, placeableY)
    }
  }
)

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
  HelloComposeTheme {
    Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
  }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
  HelloComposeTheme {
    Text("Hi there!", Modifier.padding(top = 32.dp))
  }
}