package com.devx.matchmate.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devx.matchmate.ui.profileMatch.ProfileMatchScreen

@Composable
fun AppNavGraph(paddingValues: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        navController = navController,
        startDestination = ScreenRoute.ProfileMatch.route,
    ) {
        composable(route = ScreenRoute.ProfileMatch.route) {
            ProfileMatchScreen()
        }
    }
}
