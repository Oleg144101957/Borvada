package com.bo.va.da76.model.data.tictactoe

object TicTacToeLogic {
    fun newBoard(config: TicTacToeConfig): TicTacToeBoard =
        TicTacToeBoard(
            size = config.size,
            cells = List(config.size) { List(config.size) { TicTacToeCell.EMPTY } }
        )

    /**
     * Make a move. Returns a new board if the move is possible, otherwise the same one.
     */
    fun makeMove(board: TicTacToeBoard, row: Int, col: Int, player: TicTacToeCell): TicTacToeBoard {
        if (board.cells[row][col] != TicTacToeCell.EMPTY) return board
        val newCells = board.cells.map { it.toMutableList() }
        newCells[row][col] = player
        return board.copy(cells = newCells)
    }

    /**
     * Check the winner (X, O, or null if not yet present).
     */
    fun checkWinner(board: TicTacToeBoard): TicTacToeCell? {
        val s = board.size
        val c = board.cells
        // Check rows and columns
        for (i in 0 until s) {
            if (c[i].all { it == TicTacToeCell.X }) return TicTacToeCell.X
            if (c[i].all { it == TicTacToeCell.O }) return TicTacToeCell.O
            if ((0 until s).all { c[it][i] == TicTacToeCell.X }) return TicTacToeCell.X
            if ((0 until s).all { c[it][i] == TicTacToeCell.O }) return TicTacToeCell.O
        }
        // Diagonals
        if ((0 until s).all { c[it][it] == TicTacToeCell.X }) return TicTacToeCell.X
        if ((0 until s).all { c[it][it] == TicTacToeCell.O }) return TicTacToeCell.O
        if ((0 until s).all { c[it][s - 1 - it] == TicTacToeCell.X }) return TicTacToeCell.X
        if ((0 until s).all { c[it][s - 1 - it] == TicTacToeCell.O }) return TicTacToeCell.O
        return null
    }

    /**
     * Check if there is a draw (all cells are filled, there is no winner).
     */
    fun isDraw(board: TicTacToeBoard): Boolean =
        board.cells.flatten().none { it == TicTacToeCell.EMPTY } && checkWinner(board) == null
} 