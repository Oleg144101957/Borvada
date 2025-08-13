package com.bo.va.da76.ui.screens.games.tictactoe

import android.content.pm.ActivityInfo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bo.va.da76.MainActivity
import com.bo.va.da76.R
import com.bo.va.da76.model.data.tictactoe.TicTacToeCell
import com.bo.va.da76.ui.elements.Background
import com.bo.va.da76.ui.elements.DefaultButton
import com.bo.va.da76.ui.theme.Blue
import com.bo.va.da76.ui.theme.DefTextStyle
import com.bo.va.da76.ui.theme.Red
import com.bo.va.da76.util.lockOrientation

/**
 * Composable screen for the game "Tic Tac Toe".
 * Supports different field sizes via ViewModel.
 */
@Composable
fun TicTacToeScreen(
    paddingValues: PaddingValues,
    viewModel: TicTacToeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? MainActivity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val board = viewModel.board
    val currentPlayer = viewModel.currentPlayer
    val winner = viewModel.winner
    val isDraw = viewModel.isDraw
    val isMoveEnabled = viewModel.isMoveEnabled
    val size = board.size

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Game status
            Text(
                text = when {
                    winner != null -> stringResource(R.string.winner) + when (winner) {
                        TicTacToeCell.X -> "X"; TicTacToeCell.O -> "O"; else -> ""
                    }

                    isDraw -> stringResource(R.string.a_draw)
                    else -> stringResource(R.string.the_move) + when (currentPlayer) {
                        TicTacToeCell.X -> "X"; TicTacToeCell.O -> "O"; else -> ""
                    }
                },
                fontSize = 24.sp,
                style = DefTextStyle
            )
            Spacer(Modifier.height(32.dp))
            // Game grid
            for (row in 0 until size) {
                Row(
                    modifier = Modifier.height(64.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (col in 0 until size) {
                        val cell = board.cells[row][col]
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .border(
                                    BorderStroke(2.dp, Color(0xFFC40605)),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = isMoveEnabled && cell == TicTacToeCell.EMPTY) {
                                    viewModel.makeMove(row, col)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when (cell) {
                                    TicTacToeCell.X -> "X"
                                    TicTacToeCell.O -> "O"
                                    TicTacToeCell.EMPTY -> ""
                                },
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (cell) {
                                    TicTacToeCell.X -> Red
                                    TicTacToeCell.O -> Blue
                                    else -> Color.Black
                                }
                            )
                        }
                        if (col < size - 1) Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                if (row < size - 1) Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(32.dp))
            DefaultButton(text = stringResource(R.string.new_game)) {
                viewModel.newGame()
            }
        }
    }
}
