package com.bo.va.da76.model.data.sudoku

object SudokuGenerator {
    /**
     * Generates a valid Sudoku board with one solution.
     * @param config Game configuration
     * @return SudokuBoard
     */
    fun generate(config: SudokuConfig): SudokuBoard {
        val size = config.size
        val full = Array(size) { IntArray(size) }
        SudokuSolver.solve(full, size)
        // Delete cells, leaving initialCells hints
        val cells = removeCells(full, config.initialCells)
        return SudokuBoard(
            size = size,
            cells = cells.map { row ->
                row.map { (value, isInitial) ->
                    SudokuCell(
                        value,
                        isInitial
                    )
                }
            }
        )
    }

    /**
     * Generates SudokuBoard and also returns a reference solution (fully filled field)
     */
    fun generateWithSolution(config: SudokuConfig): Pair<SudokuBoard, List<List<Int>>> {
        val size = config.size
        val full = Array(size) { IntArray(size) }
        SudokuSolver.solve(full, size)
        val solution = full.map { it.toList() }
        val cells = removeCells(full, config.initialCells)
        val board = SudokuBoard(
            size = size,
            cells = cells.map { row ->
                row.map { (value, isInitial) ->
                    SudokuCell(
                        value,
                        isInitial
                    )
                }
            }
        )
        return board to solution
    }

    private fun removeCells(full: Array<IntArray>, clues: Int): List<List<Pair<Int?, Boolean>>> {
        val size = full.size
        val total = size * size
        val positions = (0 until total).shuffled().toMutableList()
        val toRemove = total - clues
        val result = Array(size) { i -> Array(size) { j -> full[i][j] to true } }
        var removed = 0
        while (removed < toRemove && positions.isNotEmpty()) {
            val pos = positions.removeAt(0)
            val row = pos / size
            val col = pos % size
            val backup = result[row][col].first
            result[row][col] = 0 to false
            // Checking if there is one solution left
            val test = Array(size) { i -> IntArray(size) { j -> result[i][j].first ?: 0 } }
            var count = 0

            fun countSolutions(r: Int = 0, c: Int = 0): Boolean {
                for (i in 0 until size) for (j in 0 until size) if (test[i][j] == 0) {
                    for (num in 1..size) if (SudokuSolver.isSafe(test, i, j, num, size)) {
                        test[i][j] = num
                        if (countSolutions(i, j)) return true
                        test[i][j] = 0
                    }
                    return false
                }
                count++
                return count > 1
            }

            count = 0
            countSolutions()
            if (count > 1) {
                result[row][col] = backup to true
            } else {
                removed++
            }
        }
        return result.map { row -> row.map { it } }
    }
} 