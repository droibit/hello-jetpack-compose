package com.example.android.hello_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.android.hello_compose.RallyScreen.Accounts
import com.example.android.hello_compose.RallyScreen.Bills
import com.example.android.hello_compose.RallyScreen.Overview
import com.example.android.hello_compose.data.UserData
import com.example.android.hello_compose.ui.accounts.AccountsBody
import com.example.android.hello_compose.ui.accounts.SingleAccountBody
import com.example.android.hello_compose.ui.bills.BillsBody
import com.example.android.hello_compose.ui.components.RallyTabRow
import com.example.android.hello_compose.ui.overview.OverviewBody
import com.example.android.hello_compose.ui.theme.RallyTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RallyApp()
    }
  }
}

@Composable
fun RallyApp() {
  RallyTheme {
    val allScreens = RallyScreen.values().toList()
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = RallyScreen.fromRoute(
      backstackEntry.value?.destination?.route
    )
    Scaffold(
      topBar = {
        RallyTabRow(
          allScreens = allScreens,
          onTabSelected = { screen ->
            navController.navigate(screen.name)
          },
          currentScreen = currentScreen,
        )
      }
    ) { innerPadding ->
      NavHost(
        navController = navController,
        startDestination = Overview.name,
        modifier = Modifier.padding(innerPadding)
      ) {
        composable(Overview.name) {
          OverviewBody(
            onClickSeeAllAccounts = {
              navController.navigate(Accounts.name)
            },
            onClickSeeAllBills = {
              navController.navigate(Bills.name)
            },
            onAccountClick = { name ->
              navigateToSingleAccount(
                navController = navController,
                accountName = name
              )
            }
          )
        }
        composable(Accounts.name) {
          AccountsBody(
            accounts = UserData.accounts,
            onAccountClick = { name ->
              navController.navigate("${Accounts.name}/$name")
            }
          )
        }
        composable(Bills.name) {
          BillsBody(bills = UserData.bills)
        }

        val accountsName = Accounts.name
        composable(
          route = "$accountsName/{name}",
          arguments = listOf(
            navArgument("name") {
              type = NavType.StringType
            }
          ),
          deepLinks = listOf(
            navDeepLink {
              uriPattern = "rally://$accountsName/{name}"
            }
          )
        ) { entry ->
          val accountName = entry.arguments?.getString("name")
          val account = UserData.getAccount(accountName)
          SingleAccountBody(account)
        }
      }
    }
  }
}

private fun navigateToSingleAccount(
  navController: NavController,
  accountName: String
) {
  navController.navigate("${Accounts.name}/$accountName")
}