package com.example.composeapp.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.composeapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        )
        delay(3000L)
        navController.navigate("home_screen")
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.transport),
            contentDescription = "logo",
            contentScale = ContentScale.Crop
        )
    }
}