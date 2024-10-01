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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.composeapp.navigation.Navigation
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           Navigation()
        }
    }

    @Composable
    fun Timer(
        time: Long,
        handleColor: Color,
        strokeWidth: Dp = 5.dp,
        inactiveColor: Color,
        activeColor: Color,
        initialValue: Float = 1f,
        modifier: Modifier = Modifier
    ) {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        var value by remember {
            mutableFloatStateOf(initialValue)
        }
        var currentTime by remember {
            mutableLongStateOf(time)
        }
        var isTimerRunning by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
            if (currentTime >= 1 && isTimerRunning) {
                delay(1000L)
                currentTime -= 1000L
                value = currentTime / time.toFloat()
            }
        }
        Box(contentAlignment = Alignment.Center,
            modifier = modifier.onSizeChanged { size = it }) {
            Canvas(modifier = modifier) {
                drawArc(
                    color = inactiveColor,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                )
                drawArc(
                    color = activeColor,
                    startAngle = -215f,
                    sweepAngle = 250f * value,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                )
                val center = Offset(size.width / 2f, size.height / 2f)
                val beta = (250f * value + 145f) * (Math.PI / 180f).toFloat()
                val r = size.width / 2f
                val a = cos(beta) * r
                val b = sin(beta) * r
                drawPoints(
                    listOf(
                        Offset(center.x + a, center.y + b),
                    ),
                    pointMode = PointMode.Points,
                    strokeWidth = (strokeWidth * 3f).toPx(),
                    color = handleColor,
                    cap = StrokeCap.Round
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (currentTime / 1000L).toString(),
                color = Color.Black,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    if (currentTime <= 0L) {
                        currentTime = time
                        isTimerRunning = true
                    } else {
                        isTimerRunning = !isTimerRunning
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isTimerRunning || currentTime <= 0L) Color.Green
                    else Color.Red
                )
            ) {
                Text(text = if (isTimerRunning && currentTime >= 0L) "stop" else "start")
            }
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
        radius: Dp = 50.dp
    ) {
        var animPlayed by remember {
            mutableStateOf(false)
        }

        val currPercent by animateFloatAsState(
            targetValue = if (animPlayed) percentage else 0f, animationSpec = tween(
                durationMillis = animDuration, delayMillis = animDelay
            )
        )

        LaunchedEffect(key1 = true) {
            animPlayed = true
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(radius * 2f)) {
            Canvas(modifier = Modifier.size(radius * 2f)) {
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360 * currPercent,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            Text(
                text = "${(currPercent * number).toInt()}%",
                color = Color.Black,
                fontSize = TextUnit(value = fontSize.value, type = TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
        }
    }

//    @Composable
//    fun produceStateDemo(count: Int): State<Int> {
//        return produceState(initialValue = 1) {
//            while (value < count) {
//                delay(1000L)
//                value++
//            }
//        }
//    }

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
                    data = if (bundle) "Airplane mode enabled"
                    else "Airplane mode disabled"
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

