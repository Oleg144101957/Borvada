package com.bo.va.da76.ui.screens.games.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bo.va.da76.model.data.tictactoe.TicTacToeCell
import com.bo.va.da76.model.data.tictactoe.TicTacToeConfig
import com.bo.va.da76.model.data.tictactoe.TicTacToeLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the game "Tic Tac Toe" with support for different field sizes.
 * The default is the classic 3x3 field.
 */
@HiltViewModel
class TicTacToeViewModel @Inject constructor() : ViewModel() {
    private val config: TicTacToeConfig = TicTacToeConfig(size = 3)

    // Playing field condition
    var board by mutableStateOf(TicTacToeLogic.newBoard(config))
        private set

    // Current player (X or O)
    var currentPlayer by mutableStateOf(TicTacToeCell.X)
        private set

    // Winner (X, O or null if not yet determined)
    var winner by mutableStateOf<TicTacToeCell?>(null)
        private set

    // Was there a draw?
    var isDraw by mutableStateOf(false)
        private set

    // Is it allowed to make a move?
    var isMoveEnabled by mutableStateOf(true)
        private set

    /**
     * Start a new game with the current config.
     */
    fun newGame() {
        board = TicTacToeLogic.newBoard(config)
        currentPlayer = TicTacToeCell.X
        winner = null
        isDraw = false
        isMoveEnabled = true
    }

    /**
     * Make a move to cell [row], [col].
     * If the game is over or the cell is occupied, do nothing.
     */
    fun makeMove(row: Int, col: Int) {
        if (!isMoveEnabled || board.cells[row][col] != TicTacToeCell.EMPTY) return
        // Updating the field via TicTacToeLogic.makeMove
        board = TicTacToeLogic.makeMove(board, row, col, currentPlayer)
        // Winner verification
        val win = TicTacToeLogic.checkWinner(board)
        if (win != null) {
            winner = win
            isMoveEnabled = false
            return
        }
        // Check for a draw
        if (TicTacToeLogic.isDraw(board)) {
            isDraw = true
            isMoveEnabled = false
            return
        }
        // Player change
        currentPlayer = if (currentPlayer == TicTacToeCell.X) TicTacToeCell.O else TicTacToeCell.X
    }
} 