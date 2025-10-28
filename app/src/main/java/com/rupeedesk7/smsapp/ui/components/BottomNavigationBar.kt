package com.rupeedesk7.smsapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@Composable
fun BottomNavigationBar(
    navController: NavController,
    activeRoute: String
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val items = listOf(
            NavItem("dashboard", "Home", Icons.Filled.Home),
            NavItem("sms", "SMS", Icons.Filled.Send),
            NavItem("tasks", "Tasks", Icons.Filled.Task),
            NavItem("withdraw", "Wallet", Icons.Filled.AccountBalanceWallet),
            NavItem("profile", "Profile", Icons.Filled.Person)
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = activeRoute == item.route,
                onClick = {
                    if (activeRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("dashboard") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

data class NavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)