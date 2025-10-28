package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, activeRoute = "withdraw") },
        topBar = { CenterAlignedTopAppBar(title = { Text("Withdraw") }) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Withdraw Balance", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text("Current Balance: ₹204.37", color = Color(0xFF4CAF50), fontSize = 18.sp)

            Spacer(Modifier.height(30.dp))
            Button(
                onClick = { /* TODO: Withdraw Logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047))
            ) {
                Icon(Icons.Filled.AccountBalanceWallet, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Withdraw Now", color = Color.White, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(20.dp))
            Text(
                "Minimum withdrawal ₹100. Process may take up to 24 hours.",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}