package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fontFamily = FontFamily(
            Font(R.font.poppins_bold, FontWeight.Bold),
            Font(R.font.poppins_thin, FontWeight.Thin),
            Font(R.font.poppins_light, FontWeight.Light),
            Font(R.font.poppins_italic),
            Font(R.font.poppins_medium, FontWeight.Medium),
        )
        setContent {
            val painter = painterResource(R.drawable.shark)
            val desc = ""
            val title = "Shark"
            Box(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(.5f)
            ) {
                ImageCard(desc = desc, title = title, painter = painter, fontFamily = fontFamily)
            }
        }
    }

    @Composable
    fun ImageCard(
        desc: String,
        title: String,
        painter: Painter,
        modifier: Modifier = Modifier,
        fontFamily: FontFamily
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Box(modifier = modifier.fillMaxHeight(.5f)) {
                Image(
                    painter = painter,
                    contentDescription = desc,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 700f
                            )
                        )
                )
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(8.dp), contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                              style = SpanStyle(
                                  color = Color.Red,
                                  fontSize = 20.sp
                              )
                            ){
                                append("T")
                            }
                            append("iger ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Red,
                                    fontSize = 20.sp
                                )
                            ){
                                append("S")
                            }
                            append("hark is a solitary, mostly nocturnal hunter.")
                        },
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Start,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
            }
        }
    }
}
