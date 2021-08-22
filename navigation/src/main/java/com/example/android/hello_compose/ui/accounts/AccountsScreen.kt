package com.example.android.hello_compose.ui.accounts

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.android.hello_compose.R.string
import com.example.android.hello_compose.data.Account
import com.example.android.hello_compose.ui.components.AccountRow
import com.example.android.hello_compose.ui.components.StatementBody

@Composable
fun AccountsBody(
  accounts: List<Account>,
  onAccountClick: (String) -> Unit = {}
) {
  StatementBody(
    modifier = Modifier.semantics { contentDescription = "Accounts Screen" },
    items = accounts,
    colors = { account -> account.color },
    amounts = { account -> account.balance },
    amountsTotal = accounts.map { account -> account.balance }.sum(),
    circleLabel = stringResource(string.total),
    rows = { account ->
      AccountRow(
        modifier = Modifier.clickable {
          onAccountClick(account.name)
        },
        name = account.name,
        number = account.number,
        amount = account.balance,
        color = account.color,
      )
    }
  )
}

@Composable
fun SingleAccountBody(account: Account) {
  StatementBody(
    items = listOf(account),
    colors = { account.color },
    amounts = { account.balance },
    amountsTotal = account.balance,
    circleLabel = account.name,
  ) { row ->
    AccountRow(
      name = row.name,
      number = row.number,
      amount = row.balance,
      color = row.color
    )
  }
}