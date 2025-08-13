package com.bo.va.da76.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bo.va.da76.R
import com.bo.va.da76.ui.theme.DefFont
import com.bo.va.da76.ui.theme.DefTextStyle

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    imageRes: Int = R.drawable.b_btn,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.7f)
            .clickable(enabled = enabled) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(imageRes),
            imageRes.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text,
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = DefFont,
            textAlign = TextAlign.Center,
            lineHeight = TextUnit(32f, TextUnitType.Sp)
        )
    }
}

@Composable
fun SmallButton(text: String, onClick: () -> Unit) {
    Box(
        Modifier
            .size(64.dp)
            .padding(2.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.b_btn_small),
            R.drawable.b_btn_small.toString(),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text,
            fontSize = 32.sp,
            style = DefTextStyle,
            textAlign = TextAlign.Center
        )
    }
}
