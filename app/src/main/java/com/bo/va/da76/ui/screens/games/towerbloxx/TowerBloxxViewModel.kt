package com.bo.va.da76.ui.screens.games.towerbloxx

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bo.va.da76.model.data.towerbloxx.TowerBloxxBlock
import com.bo.va.da76.model.data.towerbloxx.TowerBloxxGameLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DATASTORE_NAME = "towerbloxx_prefs"
private val HIGH_SCORE_KEY = intPreferencesKey("high_score")

val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

// Game state for user interface
sealed class TowerBloxxUiState {
    data object Loading : TowerBloxxUiState()
    data class Playing(
        val blocks: List<TowerBloxxBlock>,
        val fallingBlock: TowerBloxxBlock?,
        val craneX: Float,
        val score: Int,
        val highScore: Int,
        val lives: Int,
        val combo: Int,
        val isGameOver: Boolean
    ) : TowerBloxxUiState()

    data class GameOver(val score: Int, val highScore: Int) : TowerBloxxUiState()
}

@HiltViewModel
class TowerBloxxViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val game = TowerBloxxGameLogic()
    private val _uiState = MutableStateFlow<TowerBloxxUiState>(TowerBloxxUiState.Loading)
    val uiState: StateFlow<TowerBloxxUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch { loadHighScore() }
        // Initial start without baseTowerY (temporarily 700f, UI will restart immediately)
        startGame(700f)
    }

    fun startGame(baseTowerY: Float) {
        game.startGame(baseTowerY)
        updateUi()
    }

    fun onTap() {
        if (game.isGameOver) return
        if (!game.isBlockDropping) {
            game.dropBlock()
            updateUi()
        }
    }

    // Called after the fall animation completes.
    fun onBlockLanded() {
        game.onBlockLanded()
        if (game.isGameOver) {
            saveHighScoreIfNeeded()
            _uiState.value = TowerBloxxUiState.GameOver(game.score, game.highScore)
        } else {
            updateUi()
        }
    }

    fun moveCrane() {
        game.moveCrane()
        updateUi()
    }

    private fun updateUi() {
        _uiState.value = TowerBloxxUiState.Playing(
            blocks = game.tower.blocks.toList(),
            fallingBlock = game.fallingBlock,
            craneX = game.craneX,
            score = game.score,
            highScore = game.highScore,
            lives = game.lives,
            combo = game.combo,
            isGameOver = game.isGameOver
        )
    }

    private suspend fun loadHighScore() {
        val prefs = context.dataStore.data.first()
        val highScore = prefs[HIGH_SCORE_KEY] ?: 0
        game.updateHighScore(highScore)
    }

    private fun saveHighScoreIfNeeded() {
        if (game.score > game.highScore) {
            viewModelScope.launch {
                context.dataStore.edit { prefs ->
                    prefs[HIGH_SCORE_KEY] = game.score
                }
                game.updateHighScore(game.score)
            }
        }
    }
} 