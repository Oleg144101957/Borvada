package com.bo.va.da76.ui.screens.games.sudoku

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bo.va.da76.MainActivity
import com.bo.va.da76.R
import com.bo.va.da76.model.data.sudoku.SudokuBoard
import com.bo.va.da76.model.data.sudoku.SudokuCell
import com.bo.va.da76.ui.elements.Background
import com.bo.va.da76.ui.elements.DefaultButton
import com.bo.va.da76.ui.elements.SmallButton
import com.bo.va.da76.ui.theme.Blue
import com.bo.va.da76.ui.theme.DefFont
import com.bo.va.da76.ui.theme.Red
import com.bo.va.da76.util.lockOrientation
import kotlinx.coroutines.delay

@Composable
fun SudokuScreen(
    paddingValues: PaddingValues,
    viewModel: SudokuViewModel = hiltViewModel()
) {
    val board by viewModel.board.collectAsState()
    val selected by viewModel.selected.collectAsState()
    val checkResult by viewModel.checkResult.collectAsState()
    val showDialog = checkResult != null

    val context = LocalContext.current
    val activity = context as? MainActivity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Background()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            board?.let { b ->
                SudokuBoardView(
                    board = b,
                    selected = selected,
                    onCellClick = { row, col -> viewModel.onCellClick(row, col) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            NumberPad(
                onNumber = { viewModel.onNumberInput(it) },
                onErase = { viewModel.onErase() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                DefaultButton(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    text = stringResource(R.string.new_game)
                ) {
                    viewModel.newGame()
                }
                DefaultButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.check)
                ) {
                    viewModel.checkSolution()
                }
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.newGame() },
                    containerColor = Color.White,
                    title = {
                        if (checkResult == true) {
                            Text(
                                stringResource(R.string.congratulations),
                                color = Color.Black,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = DefFont
                            )
                        } else {
                            Text(
                                stringResource(R.string.try_again_there_is_an_error_somewhere),
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = DefFont
                            )
                        }
                    },
                    text = {
                        if (checkResult == true) {
                            VictoryAnimation()
                        } else {
                            Text(
                                stringResource(R.string.check_carefully_all),
                                color = Color.Black,
                                fontFamily = DefFont
                            )
                        }
                    },
                    confirmButton = {
                        DefaultButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.ok)
                        ) {
                            viewModel.newGame()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SudokuBoardView(
    board: SudokuBoard,
    selected: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit,
) {
    Column {
        for (row in 0 until board.size) {
            Row {
                for (col in 0 until board.size) {
                    val cell = board.cells[row][col]
                    val isSelected = selected == row to col
                    SudokuCellView(
                        cell = cell,
                        isSelected = isSelected,
                        onClick = { onCellClick(row, col) }
                    )
                }
            }
        }
    }
}

@Composable
fun SudokuCellView(
    cell: SudokuCell,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .padding(1.dp)
            .background(
                if (isSelected) Blue else if (cell.isInitial) Red else Color.White,
                RoundedCornerShape(4.dp)
            )
            .clickable(enabled = !cell.isInitial, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = cell.value?.toString() ?: "",
            fontSize = 18.sp,
            fontWeight = if (cell.isInitial) FontWeight.Bold else FontWeight.Normal,
            color = if (cell.isInitial) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            fontFamily = DefFont
        )
    }
}

@Composable
fun NumberPad(
    onNumber: (Int) -> Unit,
    onErase: () -> Unit,
) {
    Column {
        for (row in 0 until 3) {
            Row {
                for (col in 1..3) {
                    val num = row * 3 + col
                    SmallButton(num.toString()) {
                        onNumber(num)
                    }
                    // Add a delete button after "6"
                    if (row == 1 && col == 3) {
                        SmallButton(stringResource(R.string.delete_button)) {
                            onErase()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VictoryAnimation() {
    val colors = listOf(Red, Blue)
    val anim = remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        repeat(30) {
            anim.intValue = it % colors.size
            delay(120)
        }
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.victory),
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = colors[anim.intValue],
            textAlign = TextAlign.Center
        )
    }
} 