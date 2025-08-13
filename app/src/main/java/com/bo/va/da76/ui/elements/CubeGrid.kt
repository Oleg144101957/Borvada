package com.bo.va.da76.ui.elements

import androidx.annotation.IntRange
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun CubeGrid(
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(60.dp, 60.dp),
    durationMillis: Int = 1000,
    color: Color = Color(0xFFC40605),
) {
    val transition = rememberInfiniteTransition()

    val durationPerFraction = durationMillis / 2

    val rectSizeMultiplier1 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0f,
        durationMillis = durationPerFraction,
        delayMillis = durationMillis / 4,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    val rectSizeMultiplier2 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0f,
        durationMillis = durationPerFraction,
        offsetMillis = durationPerFraction / 4,
        delayMillis = durationMillis / 4,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    val rectSizeMultiplier3 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0f,
        durationMillis = durationPerFraction,
        offsetMillis = durationPerFraction / 4 * 2,
        delayMillis = durationMillis / 4,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    val rectSizeMultiplier4 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0f,
        durationMillis = durationPerFraction,
        offsetMillis = durationPerFraction / 4 * 3,
        delayMillis = durationMillis / 4,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )
    val rectSizeMultiplier5 = transition.fractionTransition(
        initialValue = 1f,
        targetValue = 0f,
        durationMillis = durationPerFraction,
        offsetMillis = durationPerFraction / 4 * 4,
        delayMillis = durationMillis / 4,
        repeatMode = RepeatMode.Reverse,
        easing = EaseInOut
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier3.value),
                    color = color
                ) {

                }
            }
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier4.value),
                    color = color
                ) {

                }
            }
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier5.value),
                    color = color
                ) {

                }
            }
        }
        Row {
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier2.value),
                    color = color
                ) {

                }
            }
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier3.value),
                    color = color
                ) {

                }
            }
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier4.value),
                    color = color
                ) {

                }
            }
        }
        Row {
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier1.value),
                    color = color
                ) {

                }
            }
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier2.value),
                    color = color
                ) {

                }
            }
            Box(modifier = Modifier.size(size / 3), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.size(size / 3 * rectSizeMultiplier3.value),
                    color = color
                ) {

                }
            }
        }
    }
}

@Composable
internal fun InfiniteTransition.fractionTransition(
    initialValue: Float,
    targetValue: Float,
    @IntRange(from = 1, to = 4) fraction: Int = 1,
    durationMillis: Int,
    delayMillis: Int = 0,
    offsetMillis: Int = 0,
    repeatMode: RepeatMode = RepeatMode.Restart,
    easing: Easing = FastOutSlowInEasing
): State<Float> {
    return animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                this.durationMillis = durationMillis
                this.delayMillis = delayMillis
                initialValue at 0 using easing
                when (fraction) {
                    1 -> {
                        targetValue at durationMillis using easing
                    }

                    2 -> {
                        targetValue / fraction at durationMillis / fraction using easing
                        targetValue at durationMillis using easing
                    }

                    3 -> {
                        targetValue / fraction at durationMillis / fraction using easing
                        targetValue / fraction * 2 at durationMillis / fraction * 2 using easing
                        targetValue at durationMillis using easing
                    }

                    4 -> {
                        targetValue / fraction at durationMillis / fraction using easing
                        targetValue / fraction * 2 at durationMillis / fraction * 2 using easing
                        targetValue / fraction * 3 at durationMillis / fraction * 3 using easing
                        targetValue at durationMillis using easing
                    }
                }
            },
            repeatMode,
            StartOffset(offsetMillis)
        )
    )
}

val EaseInOut = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)
