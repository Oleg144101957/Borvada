package com.bo.va.da76.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bo.va.da76.R
import com.bo.va.da76.ui.elements.Background
import com.bo.va.da76.ui.theme.DefFont

@Composable
fun AboutScreen(paddingValues: PaddingValues) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.about),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = DefFont
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
