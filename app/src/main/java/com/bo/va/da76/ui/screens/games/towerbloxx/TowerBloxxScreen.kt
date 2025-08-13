package com.bo.va.da76.ui.screens.games.towerbloxx

import android.content.pm.ActivityInfo
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bo.va.da76.MainActivity
import com.bo.va.da76.R
import com.bo.va.da76.ui.elements.Background
import com.bo.va.da76.ui.elements.DefaultButton
import com.bo.va.da76.ui.theme.Blue
import com.bo.va.da76.ui.theme.DefFont
import com.bo.va.da76.ui.theme.DefTextStyle
import com.bo.va.da76.ui.theme.Red
import com.bo.va.da76.util.lockOrientation
import kotlinx.coroutines.delay

@Composable
fun TowerBloxxScreen(
    paddingValues: PaddingValues,
    viewModel: TowerBloxxViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? MainActivity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val uiState by viewModel.uiState.collectAsState()
    val density = LocalDensity.current
    val screenHeightPx = LocalContext.current.resources.displayMetrics.heightPixels.toFloat()
    val availableHeightDp = with(density) { screenHeightPx.toDp() }

    // Crane movement animation
    LaunchedEffect(key1 = uiState is TowerBloxxUiState.Playing && !(uiState as? TowerBloxxUiState.Playing)?.isGameOver.orFalse()) {
        while (uiState is TowerBloxxUiState.Playing && !(uiState as TowerBloxxUiState.Playing).isGameOver) {
            viewModel.moveCrane()
            delay(12L)
        }
    }

    // To animate the block falling: fix offsetYAtDrop and topBlockYAtDrop
    var offsetYAtDrop by remember { mutableFloatStateOf(0f) }
    var topBlockYAtDrop by remember { mutableFloatStateOf(0f) }
    val fallingBlock = (uiState as? TowerBloxxUiState.Playing)?.fallingBlock
    val blocks = (uiState as? TowerBloxxUiState.Playing)?.blocks ?: emptyList()
    val blockHeight = 40f
    val blockWidth = 120f
    val screenHeight = availableHeightDp.value
    val topBlockY = blocks.lastOrNull()?.y ?: (screenHeight - blockHeight)
    val focusThreshold = screenHeight / 2
    val offsetY = if (topBlockY < focusThreshold) focusThreshold - topBlockY else 0f

    val fallingBlockY = remember { Animatable(0f) }
    LaunchedEffect(fallingBlock) {
        if (fallingBlock != null && fallingBlock.isFalling) {
            offsetYAtDrop = offsetY
            topBlockYAtDrop = blocks.lastOrNull()?.y ?: (screenHeight - blockHeight)
            fallingBlockY.snapTo(0f)
            fallingBlockY.animateTo(
                (topBlockYAtDrop - blockHeight) + offsetYAtDrop,
                animationSpec = tween(durationMillis = 500)
            )
            viewModel.onBlockLanded()
        }
    }

    // Pass baseTowerY to startGame on restart
    val onRestartGame = { viewModel.startGame(availableHeightDp.value) }

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TowerBloxxScoreBar(uiState)
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = uiState is TowerBloxxUiState.Playing && !(uiState as TowerBloxxUiState.Playing).isGameOver
                    ) {
                        viewModel.onTap()
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                TowerBloxxGameField(
                    uiState = uiState,
                    fallingBlockY = if (fallingBlock != null && fallingBlock.isFalling) fallingBlockY.value else null,
                    offsetY = offsetY,
                    blockHeight = blockHeight,
                    blockWidth = blockWidth,
                    availableHeightDp = availableHeightDp.value
                )
                if (uiState is TowerBloxxUiState.GameOver) {
                    TowerBloxxGameOver(
                        score = (uiState as TowerBloxxUiState.GameOver).score,
                        highScore = (uiState as TowerBloxxUiState.GameOver).highScore,
                        onRestart = onRestartGame
                    )
                }
            }
        }
    }
}

private fun Boolean?.orFalse() = this ?: false

@Composable
fun TowerBloxxScoreBar(uiState: TowerBloxxUiState) {
    val score = when (uiState) {
        is TowerBloxxUiState.Playing -> uiState.score
        is TowerBloxxUiState.GameOver -> uiState.score
        else -> 0
    }
    val highScore = when (uiState) {
        is TowerBloxxUiState.Playing -> uiState.highScore
        is TowerBloxxUiState.GameOver -> uiState.highScore
        else -> 0
    }
    val lives = when (uiState) {
        is TowerBloxxUiState.Playing -> uiState.lives
        else -> 3
    }
    val combo = when (uiState) {
        is TowerBloxxUiState.Playing -> uiState.combo
        else -> 0
    }
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stringResource(R.string.score, score),
            style = DefTextStyle,
            fontSize = 24.sp
        )
        Text(
            stringResource(R.string.high, highScore),
            style = DefTextStyle,
            fontSize = 24.sp,
            color = Red
        )
        Row {
            repeat(lives) {
                Image(
                    painterResource(R.drawable.b_lives),
                    R.drawable.b_lives.toString(),
                    Modifier
                        .size(32.dp)
                        .padding(horizontal = 2.dp)
                )
            }
        }
        if (combo > 1) {
            Text(
                stringResource(R.string.combo, combo),
                style = DefTextStyle,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun TowerBloxxGameField(
    uiState: TowerBloxxUiState,
    fallingBlockY: Float? = null,
    offsetY: Float = 0f,
    blockHeight: Float = 40f,
    blockWidth: Float = 120f,
    availableHeightDp: Float = 800f
) {
    val screenWidth = 480f
    val scaleX = LocalContext.current.resources.displayMetrics.widthPixels / screenWidth
    val scaleY = LocalContext.current.resources.displayMetrics.heightPixels / availableHeightDp

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if (uiState is TowerBloxxUiState.Playing) {
            // Tower
            uiState.blocks.forEach { block ->
                drawRect(
                    color = Blue,
                    topLeft = Offset(
                        (block.x - blockWidth / 2) * scaleX,
                        ((block.y - blockHeight) + offsetY) * scaleY
                    ),
                    size = Size(blockWidth * scaleX, blockHeight * scaleY),
                )
            }
            // Falling block with animation: start always from Y=0 (tap), end — top block (with offsetYAtDrop)
            uiState.fallingBlock?.let { block ->
                val y = fallingBlockY ?: block.y
                val animatedY = if (fallingBlockY != null) {
                    y * scaleY // animation from 0 to target Y
                } else {
                    ((block.y - blockHeight) + offsetY) * scaleY
                }
                drawRect(
                    color = Blue,
                    topLeft = Offset(
                        (block.x - blockWidth / 2) * scaleX,
                        animatedY
                    ),
                    size = Size(blockWidth * scaleX, blockHeight * scaleY),
                )
            }
            // Crane (always at the top of the screen, no offsetY)
            drawRect(
                color = Red,
                topLeft = Offset(
                    (uiState.craneX - blockWidth / 2) * scaleX,
                    0f
                ),
                size = Size(blockWidth * scaleX, 20f * scaleY)
            )
        }
    }
}

@Composable
fun TowerBloxxGameOver(score: Int, highScore: Int, onRestart: () -> Unit) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.game_over),
                    color = Color.Black,
                    fontFamily = DefFont,
                    fontSize = 32.sp
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.score, score),
                    color = Color.Black,
                    fontFamily = DefFont,
                    fontSize = 24.sp
                )
                Text(
                    stringResource(R.string.high_score, highScore),
                    color = Red,
                    fontFamily = DefFont,
                    fontSize = 24.sp
                )
                Spacer(Modifier.height(16.dp))
                DefaultButton(text = stringResource(R.string.play_again)) {
                    onRestart()
                }
            }
        }
    }
} 