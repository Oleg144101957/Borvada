package com.bo.va.da76.ui.screens

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bo.va.da76.navigation.ScreenRoutes
import com.bo.va.da76.ui.elements.Background
import com.bo.va.da76.ui.elements.DefaultButton

@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues) {
    BackHandler {}

    val activity = LocalActivity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultButton(text = "Three in Row") {
                    navController.navigate(ScreenRoutes.ThreeInRowScreen.route)
                }
                Spacer(Modifier.height(16.dp))
                DefaultButton(text = "Tic Tac Toe") {
                    navController.navigate(ScreenRoutes.TicTacToeScreen.route)
                }
                Spacer(Modifier.height(16.dp))
                DefaultButton(text = "Sudoku") {
                    navController.navigate(ScreenRoutes.SudokuScreen.route)
                }
                Spacer(Modifier.height(16.dp))
                DefaultButton(text = "Tower Bloxx") {
                    navController.navigate(ScreenRoutes.TowerBloxxScreen.route)
                }
                Spacer(Modifier.height(16.dp))
                DefaultButton(text = "Settings") {
                    navController.navigate(ScreenRoutes.SettingsScreen.route)
                }
                Spacer(Modifier.height(16.dp))
                DefaultButton(text = "Exit") {
                    activity?.finish()
                }
            }
        }
    }
}
