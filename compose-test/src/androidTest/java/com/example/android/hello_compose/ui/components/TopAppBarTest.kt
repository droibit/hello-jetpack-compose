package com.example.android.hello_compose.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.text.toUpperCase
import com.example.android.hello_compose.RallyScreen
import com.example.android.hello_compose.RallyScreen.Accounts
import com.example.android.hello_compose.RallyScreen.Overview
import org.junit.Rule
import org.junit.Test
import java.util.Locale

class TopAppBarTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun rallyTopAppBarTest() {
    val allScreens = RallyScreen.values().toList()
    composeTestRule.setContent {
      RallyTopAppBar(
        allScreens = allScreens,
        onTabSelected = { },
        currentScreen = Accounts
      )
    }

    composeTestRule
      .onNodeWithContentDescription(Accounts.name)
      .assertIsSelected()
  }

  @Test
  fun rallyTopAppBarTest_currentLabelExists() {
    val allScreens = RallyScreen.values().toList()
    composeTestRule.setContent {
      RallyTopAppBar(
        allScreens = allScreens,
        onTabSelected = { },
        currentScreen = Accounts
      )
    }

    composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

    composeTestRule
      .onNode(
        hasText(Accounts.name.uppercase()) and
            hasParent(
              hasContentDescription(Accounts.name)
            ),
        useUnmergedTree = true
      )
      .assertExists()
  }

  @Test
  fun rallyTopAppBarTest_selectedTab() {
    val allScreens = RallyScreen.values().toList()
    composeTestRule.setContent {
      var currentScreen by rememberSaveable { mutableStateOf(Overview) }
      RallyTopAppBar(
        allScreens = allScreens,
        onTabSelected = { screen -> currentScreen = screen },
        currentScreen = currentScreen
      )
    }

    composeTestRule
      .onNodeWithContentDescription(Overview.name)
      .assertExists()
      .performClick()

    composeTestRule.onRoot(useUnmergedTree = true).printToLog("selectedTab")

    composeTestRule
      .onNode(
        hasText(Overview.name.uppercase()) and
            hasParent(
              hasContentDescription(Overview.name)
            ),
        useUnmergedTree = true
      )
      .assertExists()
  }
}