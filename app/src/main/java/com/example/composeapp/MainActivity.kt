package com.example.composeapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }

    @Composable
    fun CircularProgressBar(
        percentage: Float,
        number: Int,
        animDuration: Int = 1000,
        animDelay: Int = 0,
        strokeWidth: Dp = 8.dp,
        color: Color = Color.Green,
        fontSize: Dp = 16.dp,
        radius: Int
    ) {
        var animPlayed by remember {
            mutableStateOf(false)
        }

        val currPercent by animateFloatAsState(
            targetValue = if (animPlayed) percentage else 0f,
            animationSpec = tween(
                durationMillis = animDuration,
                delayMillis = animDelay
            )
        )

        LaunchedEffect(key1 = true) {
            animPlayed = true
        }
    }

    @Composable
    fun produceStateDemo(count: Int): State<Int> {
        return produceState(initialValue = 1) {
            while (value < count) {
                delay(1000L)
                value++
            }
        }
    }

    @Composable
    fun derivedStateDemo() {
        var count by remember {
            mutableIntStateOf(0)
        }
        val counterText by remember {
            derivedStateOf {
                "counter is $count"
            }
        }

        Button(onClick = { count++ }) {
            Text(text = counterText)
        }
    }

    @Composable
    fun WithSideEffect() {
        val count = remember {
            mutableIntStateOf(0)
        }
        SideEffect {
            Log.d("with Outer side effect", "count is ${count.intValue}")
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { count.intValue++ }) {
                Text("tap me!")
                SideEffect {
                    // Called on every recomposition
                    Log.d("with Inner side effect", "count is ${count.intValue}")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text("count ${count.intValue}")
            var name by remember {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = Unit) {
                delay(3000L)
                name = "nikhil"
            }
        }
    }

    @Composable
    fun WithLaunchedEffect() {
        var counter = remember {
            mutableIntStateOf(0)
        }
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { counter.intValue++ }) {
                Text(text = "tap me!")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text("count ${counter.intValue}")
        }
    }

    @Composable
    fun AirplaneModeScreen() {
        var data by remember {
            mutableStateOf("No state")
        }
        val context = LocalContext.current
        val broadcastReceiver = remember {
            object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    Log.d("disposable", "AirplaneModeScreen:onReceive called...")
                    val bundle = p1?.getBooleanExtra("state", false) ?: return
                    data = if (bundle)
                        "Airplane mode enabled"
                    else
                        "Airplane mode disabled"
                }

            }
        }
        DisposableEffect(key1 = true) {
            Log.d("disposable", "AirplaneModeScreen:entered DisposableEffect...")
            val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            context.applicationContext.registerReceiver(broadcastReceiver, intentFilter)
            Log.d("disposable", "AirplaneModeScreen: broadcast registered")
            onDispose {
                context.applicationContext.unregisterReceiver(broadcastReceiver)
                Log.d("disposable", "AirplaneModeScreen: broadcast unregistered")
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Status: $data")
        }
    }

}

