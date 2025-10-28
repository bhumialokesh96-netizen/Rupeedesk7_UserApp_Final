package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Task Center", style = MaterialTheme.typography.titleLarge) }
            )
        },
        // ✅ Removed activeRoute (not supported in your BottomNavigationBar)
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Available Tasks",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Invite Friends", fontWeight = FontWeight.Medium)
                    Text("Earn ₹5 per referral", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))

                    Button(onClick = {
                        // TODO: Navigate or trigger invite action
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Task,
                            contentDescription = "Task"
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Start Task")
                    }
                }
            }
        }
    }
}