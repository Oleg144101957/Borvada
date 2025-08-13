package com.bo.va.da76.model.data.sudoku

import kotlin.math.sqrt

object SudokuSolver {
    /**
     * Returns true if the Sudoku was solved. Modifies cells in-place.
     */
    fun solve(cells: Array<IntArray>, size: Int = 9): Boolean {
        for (row in 0 until size) {
            for (col in 0 until size) {
                if (cells[row][col] == 0) {
                    for (num in 1..size) {
                        if (isSafe(cells, row, col, num, size)) {
                            cells[row][col] = num
                            if (solve(cells, size)) return true
                            cells[row][col] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    fun isSafe(cells: Array<IntArray>, row: Int, col: Int, num: Int, size: Int): Boolean {
        for (x in 0 until size) if (cells[row][x] == num || cells[x][col] == num) return false
        val boxSize = sqrt(size.toDouble()).toInt()
        val boxRowStart = row - row % boxSize
        val boxColStart = col - col % boxSize
        for (i in 0 until boxSize) for (j in 0 until boxSize)
            if (cells[boxRowStart + i][boxColStart + j] == num) return false
        return true
    }
} 