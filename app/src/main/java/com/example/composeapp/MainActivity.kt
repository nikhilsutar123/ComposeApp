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
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.EaseInQuad
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var sizeState by remember {
                mutableStateOf(200.dp)
            }
            val size by animateDpAsState(targetValue = sizeState, spring(Spring.DampingRatioHighBouncy))
            val infiniteTransition = rememberInfiniteTransition()
            val color by infiniteTransition.animateColor(
                initialValue = Color.Red,
                targetValue = Color.Magenta,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Box(
                modifier = Modifier
                    .size(size)
                    .background(color = color)
                    .padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column {
                    Button(onClick = {
                        sizeState += 50.dp
                    }) {
                        Text("grow!")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = {
                        sizeState -= 50.dp
                    }) {
                        Text("shrink!")
                    }
                }
            }
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

