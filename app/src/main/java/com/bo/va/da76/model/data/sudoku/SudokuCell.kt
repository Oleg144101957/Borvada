package com.bo.va.da76.model.data.sudoku

/**
 * A cell for the Sudoku game.
 * @property value The value of the cell (1-9 or null for empty)
 * @property isInitial Is this the initial (default) value
 */
data class SudokuCell(
    val value: Int?,
    val isInitial: Boolean = false
) 