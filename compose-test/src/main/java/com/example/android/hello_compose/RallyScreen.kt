package com.example.android.hello_compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.android.hello_compose.data.UserData
import com.example.android.hello_compose.ui.accounts.AccountsBody
import com.example.android.hello_compose.ui.bills.BillsBody
import com.example.android.hello_compose.ui.overview.OverviewBody

/**
 * Screen state for Rally. Navigation is kept simple until a proper mechanism is available. Back
 * navigation is not supported.
 */
enum class RallyScreen(
  val icon: ImageVector,
  private val body: @Composable ((RallyScreen) -> Unit) -> Unit
) {
  Overview(
    icon = Icons.Filled.PieChart,
    body = { onScreenChange -> OverviewBody(onScreenChange) }
  ),
  Accounts(
    icon = Icons.Filled.AttachMoney,
    body = { AccountsBody(UserData.accounts) }
  ),
  Bills(
    icon = Icons.Filled.MoneyOff,
    body = { BillsBody(UserData.bills) }
  );

  @Composable
  fun content(onScreenChange: (RallyScreen) -> Unit) {
    body(onScreenChange)
  }
}