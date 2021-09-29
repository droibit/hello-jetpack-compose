package com.example.android.hello_compose.ui.home

import com.example.android.hello_compose.data.ExploreModel

typealias OnExploreItemClicked = (ExploreModel) -> Unit

enum class CraneScreen {
    Fly, Sleep, Eat
}
