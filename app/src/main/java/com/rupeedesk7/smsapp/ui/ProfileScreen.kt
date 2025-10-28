package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.R
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController, "profile") },
        containerColor = Color(0xFFF9FAFB)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Earn111",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Lokesh", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("VIP 8", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Balance: ₹204.37", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("Total Earned: ₹1,337.08", color = Color.White.copy(alpha = 0.9f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("UserID: 100234", color = Color.White.copy(alpha = 0.8f))
                    Text("Version: 1.0.0", color = Color.White.copy(alpha = 0.6f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Yellow Action Cards
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ProfileActionCard(
                    icon = Icons.Filled.AccountBalanceWallet,
                    title = "Withdraw Account",
                    color = Color(0xFFFFF59D)
                )
                ProfileActionCard(
                    icon = Icons.Filled.HelpOutline,
                    title = "FAQ",
                    color = Color(0xFFFFF59D)
                )
                ProfileActionCard(
                    icon = Icons.Filled.Email,
                    title = "Contact Us",
                    color = Color(0xFFFFF59D)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Log Out Button
            Button(
                onClick = { /* TODO: Logout logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log Out", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Jada Kamaye 💸",
                color = Color(0xFFFFC107),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /* TODO: Promo */ }
            )
        }
    }
}

@Composable
fun ProfileActionCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(12.dp))
            Text(title, fontSize = 16.sp, color = Color.Black)
        }
    }
}