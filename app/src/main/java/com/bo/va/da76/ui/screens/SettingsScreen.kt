package com.bo.va.da76.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bo.va.da76.navigation.ScreenRoutes
import com.bo.va.da76.ui.elements.Background
import com.bo.va.da76.ui.elements.DefaultButton
import com.bo.va.da76.ui.theme.DefFont
import com.bo.va.da76.util.CustomTabsUtil

@Composable
fun SettingsScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Background()
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Settings", color = Color.Black, fontSize = 48.sp, fontFamily = DefFont)
            Spacer(Modifier.height(16.dp))
            Text(
                "About us",
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = DefFont,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(
                        ScreenRoutes.AboutScreen.route
                    )
                }
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Privacy policy",
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = DefFont,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    CustomTabsUtil.openCustomTab(
                        context,
                        "https://sites.google.com/view/4ggfnvmc/4ggfNvMc"
                    )
                }
            )
            Spacer(Modifier.height(64.dp))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .offset(y = 50.dp),
                "OK"
            ) {
                navController.popBackStack()
            }
        }
    }
}
