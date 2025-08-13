package com.bo.va.da76.model.data.tictactoe

/**
 * Model of the playing field for the game "Tic Tac Toe".
 */
data class TicTacToeBoard(
    val size: Int,
    val cells: List<List<TicTacToeCell>>
) 