package com.rupeedesk7.smsapp.ui

import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.ui.theme.RupeedeskTheme

@Composable
fun TaskScreen(navController: NavController) {
    val ctx = LocalContext.current
    var dailyBonusClaimed by remember { mutableStateOf(false) }
    var inviteCount by remember { mutableStateOf(3) }
    var earnedFromInvites by remember { mutableStateOf(48.0) }
    var tasksCompleted by remember { mutableStateOf(7) }
    var taskGoal by remember { mutableStateOf(10) }

    RupeedeskTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Tasks & Invites", color = Color.White) },
                    backgroundColor = Color(0xFF1DB954)
                )
            },
            bottomBar = { BottomNavigationBar(active = "task", navController) }
        ) { inner ->
            Column(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .background(Color(0xFFF7F9FF))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 🪙 Daily Bonus Card
                Card(
                    backgroundColor = if (dailyBonusClaimed) Color(0xFFBDBDBD) else Color(0xFF1DB954),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Daily Login Bonus", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(
                                if (dailyBonusClaimed) "Already claimed today" else "Tap to claim ₹2.00",
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                        Button(
                            onClick = { dailyBonusClaimed = true },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (dailyBonusClaimed) Color.Gray else Color(0xFFFF9800)
                            ),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(if (dailyBonusClaimed) "Claimed" else "Claim", color = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // 🎯 Task Progress Card
                Card(
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    elevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TaskAlt, contentDescription = null, tint = Color(0xFF1DB954))
                            Spacer(Modifier.width(8.dp))
                            Text("Daily SMS Task Progress", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(12.dp))
                        LinearProgressIndicator(
                            progress = tasksCompleted / taskGoal.toFloat(),
                            color = Color(0xFF1DB954),
                            backgroundColor = Color(0xFFE0E0E0),
                            modifier = Modifier.fillMaxWidth().height(8.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("$tasksCompleted / $taskGoal completed", color = Color.Gray)
                    }
                }

                Spacer(Modifier.height(20.dp))

                // 👥 Invite Friends Card
                Card(
                    backgroundColor = Color(0xFFFFF8E1),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = Color(0xFFFF9800))
                            Spacer(Modifier.width(8.dp))
                            Text("Invite & Earn", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF9800))
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("Invite your friends and earn ₹10 for each signup!", color = Color.Gray)
                        Spacer(Modifier.height(8.dp))
                        Text("You’ve invited $inviteCount friends (₹${"%.2f".format(earnedFromInvites)})", color = Color.Black)
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "Join Rupeedesk and earn money by sending SMS 💸 https://play.google.com/store/apps/details?id=com.rupeedesk7.smsapp")
                                }
                                ctx.startActivity(Intent.createChooser(shareIntent, "Share with"))
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1DB954)),
                            shape = RoundedCornerShape(50)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("Share & Invite", color = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // ✅ Completed Tasks Summary
                Card(
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    elevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50))
                            Spacer(Modifier.width(8.dp))
                            Text("Completed Tasks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(8.dp))
                        repeat(3) {
                            Text("• Sent 100 SMS - Earned ₹15", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}