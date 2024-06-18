package com.devx.matchmate.navigation

sealed class ScreenRoute(val route: String) {
    data object ProfileMatch : ScreenRoute(route = "profile_match")
}
