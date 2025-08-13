package com.bo.va.da76.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bo.va.da76.ui.screens.AboutScreen
import com.bo.va.da76.ui.screens.ContentScreen
import com.bo.va.da76.ui.screens.HomeScreen
import com.bo.va.da76.ui.screens.NoNetworkScreen
import com.bo.va.da76.ui.screens.SettingsScreen
import com.bo.va.da76.ui.screens.games.sudoku.SudokuScreen
import com.bo.va.da76.ui.screens.games.threeinrow.ThreeInRowScreen
import com.bo.va.da76.ui.screens.games.towerbloxx.TowerBloxxScreen
import com.bo.va.da76.ui.screens.splash.SplashScreen
import com.bo.va.da76.ui.screens.games.tictactoe.TicTacToeScreen

@Composable
fun NavigationHost(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = ScreenRoutes.SplashScreen.route) {
        composable(route = ScreenRoutes.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(route = ScreenRoutes.HomeScreen.route) {
            HomeScreen(navController, innerPadding)
        }

        composable(route = ScreenRoutes.NoNetworkScreen.route) {
            NoNetworkScreen(navController, innerPadding)
        }

        composable(route = ScreenRoutes.SettingsScreen.route) {
            SettingsScreen(navController, innerPadding)
        }

        composable(route = ScreenRoutes.AboutScreen.route) {
            AboutScreen(innerPadding)
        }

        composable(route = ScreenRoutes.ThreeInRowScreen.route) {
            ThreeInRowScreen(innerPadding)
        }

        composable(route = ScreenRoutes.TicTacToeScreen.route) {
            TicTacToeScreen(innerPadding)
        }

        composable(route = ScreenRoutes.SudokuScreen.route) {
            SudokuScreen(innerPadding)
        }

        composable(route = ScreenRoutes.TowerBloxxScreen.route) {
            TowerBloxxScreen(innerPadding)
        }

        composable(
            route = "${ScreenRoutes.ContentScreen.route}/{url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            ContentScreen(navController, paddingValues = innerPadding, url)
        }
    }
}
