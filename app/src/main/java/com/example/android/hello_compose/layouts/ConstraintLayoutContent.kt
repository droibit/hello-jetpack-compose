package com.example.android.hello_compose.layouts

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.android.hello_compose.ui.theme.HelloComposeTheme

@Composable
fun ConstraintLayoutContent() {
  ConstraintLayout {
    val (button1, button2, text1, text2) = createRefs()

    Button(
      onClick = {},
      modifier = Modifier.constrainAs(button1) {
        top.linkTo(parent.top, margin = 16.dp)
      }
    ) {
      Text("Button 1")
    }

    Text("Text", Modifier.constrainAs(text1) {
      top.linkTo(button1.bottom, margin = 16.dp)
      centerAround(button1.end)
    })

    val barrier = createEndBarrier(button1, text1)
    Button(
      onClick = {},
      modifier = Modifier.constrainAs(button2) {
        top.linkTo(parent.top, margin = 16.dp)
        start.linkTo(barrier)
      }
    ) {
      Text("Button 2")
    }

    val guideline = createGuidelineFromStart(0.5f)
    Text(
      "This is a very very very very very very very very long text1.",
      Modifier.constrainAs(text2) {
        top.linkTo(text1.bottom, 16.dp)
        linkTo(start = guideline, end = parent.end)
        width = Dimension.preferredWrapContent
      }
    )
  }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutContentPreview() {
  HelloComposeTheme {
    ConstraintLayoutContent()
  }
}

@Composable
fun DecoupledConstraintLayout() {
  BoxWithConstraints {
    val constraints = if (maxWidth < maxHeight) {
      // Portrait constraints
      decoupledConstraints(margin = 16.dp)
    } else {
      // Landscape constraints
      decoupledConstraints(margin = 32.dp)
    }

    ConstraintLayout(constraints) {
      Button(
        onClick = {},
        modifier = Modifier.layoutId("button")
      ) {
        Text("Button")
      }

      Text("Text", Modifier.layoutId("text"))
    }
  }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
  return ConstraintSet {
    val button = createRefFor("button")
    val text = createRefFor("text")
    constrain(button) {
      top.linkTo(parent.top, margin = margin)
    }
    constrain(text) {
      top.linkTo(button.bottom, margin = margin)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DecoupledConstraintLayoutPreview() {
  HelloComposeTheme {
    DecoupledConstraintLayout()
  }
}
