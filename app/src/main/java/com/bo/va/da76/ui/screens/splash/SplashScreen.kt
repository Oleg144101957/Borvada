package com.bo.va.da76.ui.screens.splash

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bo.va.da76.MainActivity
import com.bo.va.da76.R
import com.bo.va.da76.model.data.LoadingState
import com.bo.va.da76.navigation.ScreenRoutes
import com.bo.va.da76.ui.elements.Background
import com.bo.va.da76.ui.elements.CubeGrid
import com.bo.va.da76.util.lockOrientation
import kotlinx.coroutines.delay
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    BackHandler {}

    val context = LocalContext.current
    val activity = context as? MainActivity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val state = viewModel.liveState.collectAsState().value

    LaunchedEffect(state) {
        when (state) {
            is LoadingState.ContentState -> {
                val url = URLEncoder.encode(state.url, StandardCharsets.UTF_8.toString())
                val route = "${ScreenRoutes.ContentScreen.route}/$url"
                navController.navigate(route)
            }

            LoadingState.HomeState -> {
                delay(1500)
                navController.navigate(ScreenRoutes.HomeScreen.route)
            }

            LoadingState.InitState -> {
                viewModel.load(context)
            }

            LoadingState.NoNetworkState -> {
                navController.navigate(ScreenRoutes.NoNetworkScreen.route)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Background(R.drawable.b_bg_loading)
        CubeGrid(modifier = Modifier.offset(y = 128.dp))
    }
}
