package com.rupeedesk7.smsapp.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinWheelScreen(navController: NavController) {
    var isSpinning by remember { mutableStateOf(false) }
    var rotation by remember { mutableStateOf(0f) }
    var prize by remember { mutableStateOf("Tap Spin to Start!") }

    val spinAnim = animateFloatAsState(
        targetValue = if (isSpinning) rotation + 1440f else rotation,
        animationSpec = tween(4000, easing = FastOutSlowInEasing),
        finishedListener = {
            isSpinning = false
            val result = listOf("₹1", "₹2", "₹5", "₹10", "Try Again", "Bonus!").random()
            prize = "🎁 You got $result"
        }
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Daily Spin", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1DB954)
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF7F9FF))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(220.dp)) {
                    val colors = listOf(
                        Color(0xFFFF9800),
                        Color(0xFF4CAF50),
                        Color(0xFF03A9F4),
                        Color(0xFFE91E63),
                        Color(0xFF9C27B0),
                        Color(0xFFFFC107)
                    )
                    rotate(spinAnim.value) {
                        for (i in 0..5) {
                            drawArc(
                                color = colors[i],
                                startAngle = i * 60f,
                                sweepAngle = 60f,
                                useCenter = true,
                                size = Size(size.width, size.height)
                            )
                        }
                    }
                }
                Text("SPIN", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!isSpinning) {
                        isSpinning = true
                        rotation += 360f * Random.nextInt(4, 8)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    if (isSpinning) "Spinning..." else "Tap to Spin 🎡",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))
            Text(prize, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1DB954))
        }
    }
}