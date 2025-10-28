package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.ui.theme.RupeedeskTheme

@Composable
fun SMSScreen(navController: NavController) {
    RupeedeskTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Rupeedesk", color = Color.White) }, backgroundColor = Color(0xFF1DB954)) },
            bottomBar = { BottomNavigationBar(active = "sms", navController) }
        ) { inner ->
            Column(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .background(Color(0xFFF7F9FF))
                    .padding(16.dp)
            ) {
                // Balance Card
                Card(
                    backgroundColor = Color(0xFF1DB954),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Balance ₹204.37", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Today's Earnings: ₹29.62", color = Color.White)
                            Text("Task Earnings: ₹12.96", color = Color.White)
                            Text("Share: ₹16.66", color = Color.White)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("UserID: U2025A1007 | Ver: 1.0.3", color = Color.White.copy(alpha = 0.8f))
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Task SIM list
                Text("Your SIM Tasks", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                SIMCard("SIM 1 – JIO 4G", 0, 100)
                SIMCard("SIM 2 – Airtel", 81, 100)

                Spacer(Modifier.height(24.dp))
                Text(
                    "💸 Jada Kamaye — Complete Tasks to Earn!",
                    color = Color(0xFFFFA000),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SIMCard(label: String, sent: Int, total: Int) {
    val progress = sent.toFloat() / total
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(label, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                IconButton(onClick = { /* refresh */ }) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                }
            }
            LinearProgressIndicator(progress = progress, color = Color(0xFFFF9800), modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Sent: $sent / $total")
                Button(
                    onClick = { /* start task */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF9800))
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(6.dp))
                    Text("Start Task", color = Color.White)
                }
            }
        }
    }
}