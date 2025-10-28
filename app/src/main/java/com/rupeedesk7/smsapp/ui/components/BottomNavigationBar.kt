package com.rupeedesk7.smsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.WalletGiftcard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(active: String, navController: NavController) {
    val items = listOf(
        NavItem("sms", "SMS", Icons.Default.Email),
        NavItem("spin", "Spin", Icons.Default.Games),
        NavItem("task", "Tasks", Icons.Default.TaskAlt),
        NavItem("profile", "Profile", Icons.Default.AccountCircle)
    )

    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = 12.dp
    ) {
        items.forEach { item ->
            val selected = active == item.route
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) Color(0xFF1DB954) else Color(0xFF9E9E9E)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) Color(0xFF1DB954) else Color.Gray
                    )
                },
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}

data class NavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)