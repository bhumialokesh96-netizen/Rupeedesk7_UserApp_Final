package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar

@Composable
fun SMSScreen(navController: NavController) {
    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Rupeedesk SMS", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF388E3C))
            Spacer(Modifier.height(16.dp))

            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF6DD))) {
                Column(Modifier.padding(16.dp)) {
                    Text("Balance: ₹204.37", fontWeight = FontWeight.Bold)
                    Text("Today's Earnings: ₹29.62")
                    Text("Task Earning: ₹12.96")
                    Text("Share Earning: ₹16.66")
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { /* Start Task Logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA000))
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Start SMS Task", color = Color.White)
            }
        }
    }
}