package com.example.android.hello_compose.ui.accounts

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.android.hello_compose.R
import com.example.android.hello_compose.data.Account
import com.example.android.hello_compose.ui.components.AccountRow
import com.example.android.hello_compose.ui.components.StatementBody

/**
 * The Accounts screen.
 */
@Composable
fun AccountsBody(accounts: List<Account>) {
  StatementBody(
    items = accounts,
    amounts = { account -> account.balance },
    colors = { account -> account.color },
    amountsTotal = accounts.map { account -> account.balance }.sum(),
    circleLabel = stringResource(R.string.total),
    rows = { account ->
      AccountRow(
        name = account.name,
        number = account.number,
        amount = account.balance,
        color = account.color
      )
    }
  )
}