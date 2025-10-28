package com.rupeedesk7.smsapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController, activeRoute: String) {
    NavigationBar(
        modifier = Modifier.height(64.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        NavigationBarItem(
            selected = activeRoute == "sms",
            onClick = { navController.navigate("sms") },
            icon = { Icon(Icons.Filled.SmartToy, contentDescription = null) },
            label = { Text("SMS") }
        )
        NavigationBarItem(
            selected = activeRoute == "tasks",
            onClick = { navController.navigate("tasks") },
            icon = { Icon(Icons.Filled.Task, contentDescription = null) },
            label = { Text("Tasks") }
        )
        NavigationBarItem(
            selected = activeRoute == "wallet",
            onClick = { navController.navigate("wallet") },
            icon = { Icon(Icons.Filled.AccountBalanceWallet, contentDescription = null) },
            label = { Text("Wallet") }
        )
        NavigationBarItem(
            selected = activeRoute == "spin",
            onClick = { navController.navigate("spin") },
            icon = { Icon(Icons.Filled.Games, contentDescription = null) },
            label = { Text("Spin") }
        )
        NavigationBarItem(
            selected = activeRoute == "profile",
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Profile") }
        )
    }
}