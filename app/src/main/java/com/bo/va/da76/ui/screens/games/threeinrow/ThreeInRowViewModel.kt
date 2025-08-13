package com.bo.va.da76.ui.screens.games.threeinrow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bo.va.da76.model.data.threeinrow.ThreeInRowBoard
import com.bo.va.da76.model.data.threeinrow.ThreeInRowConfig
import com.bo.va.da76.model.data.threeinrow.ThreeInRowLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreeInRowViewModel @Inject constructor() : ViewModel() {
    private val _board = MutableStateFlow<ThreeInRowBoard?>(null)
    val board: StateFlow<ThreeInRowBoard?> = _board

    private val config = ThreeInRowConfig()

    // To select items (swap)
    private val _selected = MutableStateFlow<Pair<Int, Int>?>(null)
    val selected: StateFlow<Pair<Int, Int>?> = _selected

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _bestScore = MutableStateFlow(0)
    val bestScore: StateFlow<Int> = _bestScore

    init {
        newGame()
    }

    fun newGame() {
        if (_score.value > _bestScore.value) {
            _bestScore.value = _score.value
        }
        _board.value = ThreeInRowLogic.generateBoard(config)
        _selected.value = null
        _score.value = 0
        processMatches() // immediately remove starting matches
    }

    fun onCellClick(row: Int, col: Int) {
        val current = _selected.value
        if (current == null) {
            _selected.value = row to col
        } else {
            if (current == row to col) {
                _selected.value = null
            } else {
                swap(current, row to col)
                _selected.value = null
            }
        }
    }

    private fun swap(from: Pair<Int, Int>, to: Pair<Int, Int>) {
        val oldBoard = _board.value ?: return
        val swapped = ThreeInRowLogic.swap(oldBoard, from, to)
        // Checking if swap resulted in a match
        val matches = ThreeInRowLogic.checkMatches(swapped)
        if (matches.isNotEmpty()) {
            _board.value = swapped
            processMatches()
        } // if there are no matches — we do not update the field (swap did not occur)
    }

    private fun processMatches() {
        viewModelScope.launch {
            var combo = 1
            while (true) {
                val board = _board.value ?: break
                val matches = ThreeInRowLogic.checkMatches(board)
                if (matches.isEmpty()) break
                _score.value += matches.size * 10 * combo
                if (_score.value > _bestScore.value) {
                    _bestScore.value = _score.value
                }
                _board.value = ThreeInRowLogic.removeAndFill(board, matches, config)
                combo++
            }
        }
    }
} 