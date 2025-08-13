package com.bo.va.da76.ui.screens.games.sudoku

import androidx.lifecycle.ViewModel
import com.bo.va.da76.model.data.sudoku.SudokuBoard
import com.bo.va.da76.model.data.sudoku.SudokuConfig
import com.bo.va.da76.model.data.sudoku.SudokuGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor() : ViewModel() {
    private val _board = MutableStateFlow<SudokuBoard?>(null)
    val board: StateFlow<SudokuBoard?> = _board

    private val config = SudokuConfig()

    private val _selected = MutableStateFlow<Pair<Int, Int>?>(null)
    val selected: StateFlow<Pair<Int, Int>?> = _selected

    // Reference solution (filled in field)
    private var solution: List<List<Int>>? = null

    private val _checkResult = MutableStateFlow<Boolean?>(null)
    val checkResult: StateFlow<Boolean?> = _checkResult

    init {
        newGame()
    }

    fun newGame() {
        val generated = SudokuGenerator.generateWithSolution(config)
        _board.value = generated.first
        solution = generated.second
        _selected.value = null
        _checkResult.value = null
    }

    fun onCellClick(row: Int, col: Int) {
        val board = _board.value ?: return
        val cell = board.cells[row][col]
        if (!cell.isInitial) {
            _selected.value = row to col
        }
    }

    fun onNumberInput(number: Int) {
        val board = _board.value ?: return
        val sel = _selected.value ?: return
        val (row, col) = sel
        val cell = board.cells[row][col]
        if (cell.isInitial) return
        val newCells = board.cells.mapIndexed { r, rowList ->
            rowList.mapIndexed { c, oldCell ->
                if (r == row && c == col) oldCell.copy(value = number) else oldCell
            }
        }
        _board.value = board.copy(cells = newCells)
    }

    fun onErase() {
        val board = _board.value ?: return
        val sel = _selected.value ?: return
        val (row, col) = sel
        val cell = board.cells[row][col]
        if (cell.isInitial) return
        val newCells = board.cells.mapIndexed { r, rowList ->
            rowList.mapIndexed { c, oldCell ->
                if (r == row && c == col) oldCell.copy(value = null) else oldCell
            }
        }
        _board.value = board.copy(cells = newCells)
    }

    fun checkSolution() {
        val board = _board.value ?: return
        val sol = solution ?: return
        val isCorrect = board.cells.withIndex().all { (i, row) ->
            row.withIndex().all { (j, cell) ->
                cell.value == sol[i][j]
            }
        }
        _checkResult.value = isCorrect
    }

    fun isCompleted(): Boolean {
        val board = _board.value ?: return false
        return board.cells.all { row -> row.all { it.value != null } }
    }
} 