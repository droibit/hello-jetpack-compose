package com.example.android.hello_compose.ui.home

import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.hello_compose.data.ExploreModel
import com.example.android.hello_compose.ui.base.CraneDrawer
import com.example.android.hello_compose.ui.base.CraneTabBar
import com.example.android.hello_compose.ui.base.CraneTabs
import com.example.android.hello_compose.ui.base.ExploreSection
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

typealias OnExploreItemClicked = (ExploreModel) -> Unit

enum class CraneScreen {
  Fly,
  Sleep,
  Eat
}

@Composable
fun CraneHome(
  onExploreItemClicked: OnExploreItemClicked,
  modifier: Modifier = Modifier,
) {
  val scaffoldState = rememberScaffoldState()
  Scaffold(
    scaffoldState = scaffoldState,
    modifier = Modifier.statusBarsPadding(),
    drawerContent = {
      CraneDrawer()
    }
  ) {
    val scope = rememberCoroutineScope()
    CraneHomeContent(
      modifier = modifier,
      onExploreItemClicked = onExploreItemClicked,
      openDrawer = {
        scope.launch {
          scaffoldState.drawerState.open()
        }
      }
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CraneHomeContent(
  onExploreItemClicked: OnExploreItemClicked,
  openDrawer: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: MainViewModel = viewModel(),
) {
  val suggestedDestinations by viewModel.suggestedDestinations.collectAsState()

  val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) }
  var tabSelected by remember { mutableStateOf(CraneScreen.Fly) }

  BackdropScaffold(
    modifier = modifier,
    scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
    frontLayerScrimColor = Color.Unspecified,
    appBar = {
      HomeTabBar(openDrawer, tabSelected, onTabSelected = { tabSelected = it })
    },
    backLayerContent = {
      SearchContent(
        tabSelected,
        viewModel,
        onPeopleChanged
      )
    },
    frontLayerContent = {
      when (tabSelected) {
        CraneScreen.Fly -> {
          ExploreSection(
            title = "Explore Flights by Destination",
            exploreList = suggestedDestinations,
            onItemClicked = onExploreItemClicked
          )
        }
        CraneScreen.Sleep -> {
          ExploreSection(
            title = "Explore Properties by Destination",
            exploreList = viewModel.hotels,
            onItemClicked = onExploreItemClicked
          )
        }
        CraneScreen.Eat -> {
          ExploreSection(
            title = "Explore Restaurants by Destination",
            exploreList = viewModel.restaurants,
            onItemClicked = onExploreItemClicked
          )
        }
      }
    }
  )
}

@Composable
private fun HomeTabBar(
  openDrawer: () -> Unit,
  tabSelected: CraneScreen,
  onTabSelected: (CraneScreen) -> Unit,
  modifier: Modifier = Modifier
) {
  CraneTabBar(
    modifier = modifier,
    onMenuClicked = openDrawer
  ) { tabBarModifier ->
    CraneTabs(
      modifier = tabBarModifier,
      titles = CraneScreen.values().map { it.name },
      tabSelected = tabSelected,
      onTabSelected = { newTab -> onTabSelected(CraneScreen.values()[newTab.ordinal]) }
    )
  }
}

@Composable
private fun SearchContent(
  tabSelected: CraneScreen,
  viewModel: MainViewModel,
  onPeopleChanged: (Int) -> Unit
) {
  when (tabSelected) {
    CraneScreen.Fly -> FlySearchContent(
      onPeopleChanged = onPeopleChanged,
      onToDestinationChanged = { viewModel.toDestinationChanged(it) }
    )
    CraneScreen.Sleep -> SleepSearchContent(
      onPeopleChanged = onPeopleChanged
    )
    CraneScreen.Eat -> EatSearchContent(
      onPeopleChanged = onPeopleChanged
    )
  }
}