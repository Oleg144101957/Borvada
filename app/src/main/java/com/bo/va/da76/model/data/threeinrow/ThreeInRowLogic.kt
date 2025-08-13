package com.bo.va.da76.model.data.threeinrow

import kotlin.math.abs

object ThreeInRowLogic {
    private var gemIdCounter = 0L
    private fun nextGemId() = gemIdCounter++

    fun swap(board: ThreeInRowBoard, from: Pair<Int, Int>, to: Pair<Int, Int>): ThreeInRowBoard {
        val (fx, fy) = from
        val (tx, ty) = to
        if (abs(fx - tx) + abs(fy - ty) != 1) return board
        val newCells = board.cells.map { it.toMutableList() }
        val temp = newCells[fx][fy]
        newCells[fx][fy] = newCells[tx][ty]
        newCells[tx][ty] = temp
        return board.copy(cells = newCells)
    }

    fun checkMatches(board: ThreeInRowBoard): List<Pair<Int, Int>> {
        val matches = mutableSetOf<Pair<Int, Int>>()
        val rows = board.rows
        val cols = board.columns
        val cells = board.cells
        // Horizontal
        for (i in 0 until rows) {
            var count = 1
            for (j in 1 until cols) {
                if (cells[i][j]?.type == cells[i][j - 1]?.type && cells[i][j]?.type != null) {
                    count++
                } else {
                    if (count >= 3) for (k in j - count until j) matches.add(i to k)
                    count = 1
                }
            }
            if (count >= 3) for (k in cols - count until cols) matches.add(i to k)
        }
        // Vertical
        for (j in 0 until cols) {
            var count = 1
            for (i in 1 until rows) {
                if (cells[i][j]?.type == cells[i - 1][j]?.type && cells[i][j]?.type != null) {
                    count++
                } else {
                    if (count >= 3) for (k in i - count until i) matches.add(k to j)
                    count = 1
                }
            }
            if (count >= 3) for (k in rows - count until rows) matches.add(k to j)
        }
        return matches.toList()
    }

    fun removeAndFill(
        board: ThreeInRowBoard,
        matches: List<Pair<Int, Int>>,
        config: ThreeInRowConfig
    ): ThreeInRowBoard {
        val rows = board.rows
        val cols = board.columns
        val cells = board.cells.map { it.toMutableList() }
        // 1. Remove matches (null)
        for ((i, j) in matches) {
            cells[i][j] = null
        }
        // 2. The "fall" of gems
        for (j in 0 until cols) {
            var empty = rows - 1
            for (i in (rows - 1) downTo 0) {
                if (cells[i][j] != null) {
                    cells[empty][j] = cells[i][j]
                    if (empty != i) cells[i][j] = null
                    empty--
                }
            }
            // 3. Replenishment with new gems
            for (i in empty downTo 0) {
                val type = config.elementTypes.random()
                cells[i][j] = Gem(id = nextGemId(), type = type)
            }
        }
        return board.copy(cells = cells)
    }

    fun generateBoard(config: ThreeInRowConfig): ThreeInRowBoard {
        return ThreeInRowBoard(
            rows = config.rows,
            columns = config.columns,
            cells = List(config.rows) {
                List(config.columns) {
                    Gem(id = nextGemId(), type = config.elementTypes.random())
                }
            }
        )
    }
} 