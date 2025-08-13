package com.bo.va.da76.ui.screens.games.threeinrow

import android.content.pm.ActivityInfo
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bo.va.da76.MainActivity
import com.bo.va.da76.R
import com.bo.va.da76.ui.elements.Background
import com.bo.va.da76.ui.elements.DefaultButton
import com.bo.va.da76.ui.theme.DefTextStyle
import com.bo.va.da76.util.lockOrientation

@Composable
fun ThreeInRowScreen(
    paddingValues: PaddingValues,
    viewModel: ThreeInRowViewModel = hiltViewModel()
) {
    val board by viewModel.board.collectAsState()
    val selected by viewModel.selected.collectAsState()
    val score by viewModel.score.collectAsState()
    val bestScore by viewModel.bestScore.collectAsState()

    val moveAnimSpec = tween<IntOffset>(durationMillis = 600, easing = FastOutSlowInEasing)

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    stringResource(R.string.score, score),
                    style = DefTextStyle,
                    fontSize = 24.sp
                )
                Text(
                    stringResource(R.string.best, bestScore),
                    style = DefTextStyle,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            board?.let { b ->
                val flatList = b.cells.flatten()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(b.columns),
                    userScrollEnabled = false,
                    modifier = Modifier
                        .size((b.columns * 50).dp, (b.rows * 50).dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    itemsIndexed(flatList, key = { _, gem -> gem?.id ?: -1L }) { index, gem ->
                        val row = index / b.columns
                        val col = index % b.columns
                        val type = gem?.type
                        val isSelected = selected == row to col
                        Modifier
                            .size(40.dp)
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(if (type == null) Color.LightGray else Color.Transparent)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) Color.Black else Color.Gray,
                                shape = CircleShape
                            )
                        Box(
                            modifier = Modifier
                                .animateItem(
                                    fadeInSpec = null,
                                    fadeOutSpec = null,
                                    placementSpec = moveAnimSpec
                                )
                                .clickable(enabled = type != null) {
                                    viewModel.onCellClick(
                                        row,
                                        col
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedContent(
                                targetState = type,
                                transitionSpec = {
                                    fadeIn(animationSpec = tween(220)) + scaleIn(
                                        initialScale = 0.7f,
                                        animationSpec = tween(220)
                                    ) togetherWith
                                            fadeOut(animationSpec = tween(220))
                                }
                            ) { animType ->
                                if (animType != null) {
                                    Image(
                                        painter = painterResource(id = animType.imageRes),
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            DefaultButton(text = stringResource(R.string.new_game)) {
                viewModel.newGame()
            }
        }
    }
}
