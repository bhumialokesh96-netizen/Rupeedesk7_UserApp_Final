package com.rupeedesk7.smsapp.ui.components

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("sms") },
            icon = { Icon(Icons.Filled.SmartToy, contentDescription = "SMS") },
            label = { Text("SMS") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("task") },
            icon = { Icon(Icons.Filled.Task, contentDescription = "Tasks") },
            label = { Text("Task") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("withdraw") },
            icon = { Icon(Icons.Filled.AccountBalanceWallet, contentDescription = "Withdraw") },
            label = { Text("Wallet") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}