package com.bo.va.da76.model.data.sudoku

/**
 * Sudoku game configuration.
 * @property size Field size (usually 9)
 * @property initialCells Number of initially filled cells
 */
data class SudokuConfig(
    val size: Int = 9,
    val initialCells: Int = 30 // Number of hints (the fewer, the more difficult)
) 