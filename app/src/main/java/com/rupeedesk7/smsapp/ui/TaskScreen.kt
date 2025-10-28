package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar

@Composable
fun TaskScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController, "task") },
        containerColor = Color(0xFFF9FAFB)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Daily Tasks",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TaskItem(
                icon = Icons.Filled.Task,
                title = "Send 50 SMS",
                description = "Earn ₹8.00 by sending 50 messages",
                progress = 0.5f
            )

            Spacer(modifier = Modifier.height(12.dp))

            TaskItem(
                icon = Icons.Filled.VerifiedUser,
                title = "Verify Your Number",
                description = "Complete KYC to earn ₹10.00",
                progress = 1f
            )
        }
    }
}

@Composable
fun TaskItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    progress: Float
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color(0xFF4CAF50))
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(description, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = progress, color = Color(0xFF4CAF50))
        }
    }
}