package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                Modifier
                    .fillMaxSize()

            ) {
                val names = listOf("Alice", "Bob", "Charlie", "David", "Eve")
                val selectedName = remember {
                    mutableStateOf(names[0])
                }
                NameBox(
                    Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    names,
                ) {
                    selectedName.value = it
                }
                Box(
                    modifier = Modifier
                        .background(Color.Magenta)
                        .weight(1f)
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = selectedName.value, fontSize = 60.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun NameBox(
    modifier: Modifier = Modifier,
    names: List<String>,
    updateName: (String) -> Unit,
) {
    Box(modifier = modifier
        .background(Color.Cyan)
        .clickable {
            updateName(
                names.random()
            )
        }, contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello", fontSize = 60.sp, textAlign = TextAlign.Center)
    }
}
