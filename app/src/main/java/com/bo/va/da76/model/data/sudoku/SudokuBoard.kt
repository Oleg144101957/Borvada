package com.bo.va.da76.model.data.sudoku

/**
 * Model of the playing field for the game "Sudoku".
 * @property size Size of the field (usually 9)
 * @property cells Two-dimensional array of cells
 */
data class SudokuBoard(
    val size: Int = 9,
    val cells: List<List<SudokuCell>>
) 