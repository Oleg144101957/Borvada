package com.bo.va.da76.navigation

sealed class ScreenRoutes(val route: String) {

    data object SplashScreen : ScreenRoutes(SPLASH_SCREEN)
    data object HomeScreen : ScreenRoutes(HOME_SCREEN)
    data object NoNetworkScreen : ScreenRoutes(NO_NETWORK_SCREEN)
    data object SettingsScreen : ScreenRoutes(SETTINGS_SCREEN)
    data object AboutScreen : ScreenRoutes(ABOUT_SCREEN)
    data object ThreeInRowScreen : ScreenRoutes(THREE_IN_ROW_SCREEN)
    data object TicTacToeScreen : ScreenRoutes(TIC_TAC_TOE_SCREEN)
    data object SudokuScreen : ScreenRoutes(SUDOKU_SCREEN)
    data object TowerBloxxScreen : ScreenRoutes(TOWER_BLOXX_SCREEN)
    data object ContentScreen : ScreenRoutes(CONTENT_SCREEN)

    companion object {
        private const val SPLASH_SCREEN = "Splash_Screen"
        private const val HOME_SCREEN = "Home_Screen"
        private const val NO_NETWORK_SCREEN = "No_Network_Screen"
        private const val SETTINGS_SCREEN = "Settings_Screen"
        private const val ABOUT_SCREEN = "About_Screen"
        private const val THREE_IN_ROW_SCREEN = "Three_In_Row_Screen"
        private const val TIC_TAC_TOE_SCREEN = "Tic_Tac_Toe_Screen"
        private const val SUDOKU_SCREEN = "Sudoku_Screen"
        private const val TOWER_BLOXX_SCREEN = "Tower_Bloxx_Screen"
        private const val CONTENT_SCREEN = "Content_Screen"
    }
}