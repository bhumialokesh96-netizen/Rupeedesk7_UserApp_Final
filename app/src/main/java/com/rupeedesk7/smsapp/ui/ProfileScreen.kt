package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.R
import com.rupeedesk7.smsapp.ui.theme.RupeedeskTheme

@Composable
fun ProfileScreen(navController: NavController) {
    RupeedeskTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Earn111", color = Color.White) },
                    backgroundColor = Color(0xFF1DB954)
                )
            },
            bottomBar = { BottomNavigationBar(active = "profile", navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color(0xFFF7F9FF))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // User Card
                Card(
                    backgroundColor = Color(0xFF1DB954),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("📱 +91 •••• 9214", color = Color.White, fontSize = 18.sp)
                        Text("VIP 8", color = Color.White.copy(alpha = 0.8f))
                        Spacer(Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            InfoBox("Balance", "₹204.37")
                            InfoBox("Total Earned", "₹1,337.08")
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("UserID: U2025A1007", color = Color.White.copy(alpha = 0.8f))
                        Text("Version: 1.0.3", color = Color.White.copy(alpha = 0.6f))
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Action buttons
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ActionButton("Withdraw Account", Color(0xFFFFC107)) {}
                    ActionButton("FAQ", Color(0xFF4CAF50)) {}
                    ActionButton("Contact Us", Color(0xFF03A9F4)) {}
                }

                Spacer(Modifier.height(24.dp))

                // Logout
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Log Out", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    "💸 Jada Kamaye — Tap to Earn More!",
                    color = Color(0xFFFFA000),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun InfoBox(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = Color.White.copy(alpha = 0.8f))
        Text(value, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ActionButton(text: String, bg: Color, onClick: () -> Unit) {
    Card(
        backgroundColor = bg,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 2.dp),
        elevation = 6.dp
    ) {
        Box(Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
            Text(text, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}