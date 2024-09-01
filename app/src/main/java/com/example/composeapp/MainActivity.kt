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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           AirplaneModeScreen()
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
            context.applicationContext.registerReceiver(broadcastReceiver,intentFilter)
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

