package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
fun WithdrawScreen(navController: NavController) {
    RupeedeskTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Withdraw", color = Color.White) }, backgroundColor = Color(0xFF1DB954)) },
            bottomBar = { BottomNavigationBar(active = "profile", navController) }
        ) { inner ->
            Column(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .background(Color(0xFFF7F9FF))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Balance
                Card(
                    backgroundColor = Color(0xFF1DB954),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Available Balance", color = Color.White.copy(alpha = 0.8f))
                        Text("₹204.37", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(Modifier.height(24.dp))

                var method by remember { mutableStateOf("UPI") }
                var upi by remember { mutableStateOf("") }
                var amount by remember { mutableStateOf("") }

                Text("Select Withdrawal Method", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MethodChip("UPI", method == "UPI") { method = "UPI" }
                    MethodChip("Bank", method == "Bank") { method = "Bank" }
                }

                Spacer(Modifier.height(16.dp))

                if (method == "UPI") {
                    OutlinedTextField(
                        value = upi,
                        onValueChange = { upi = it },
                        label = { Text("Enter UPI ID") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    OutlinedTextField(
                        value = upi,
                        onValueChange = { upi = it },
                        label = { Text("Bank Account No.") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Enter Amount (min ₹100)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { /* trigger withdrawal */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF9800)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Withdraw Now", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(24.dp))
                Text("⚠️ Withdrawals may take up to 24 hours.", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun MethodChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        color = if (selected) Color(0xFF1DB954) else Color.LightGray,
        shape = RoundedCornerShape(50),
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(text, color = if (selected) Color.White else Color.Black)
        }
    }
}