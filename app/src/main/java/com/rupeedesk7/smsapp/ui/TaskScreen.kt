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
        topBar = { CenterAlignedTopAppBar(title = { Text("Task Center") }) },
        bottomBar = { BottomNavigationBar(navController = navController, activeRoute = "tasks") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Available Tasks", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Invite Friends", fontWeight = FontWeight.Medium)
                    Text("Earn ₹5 per referral")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { /* navigate or trigger */ }) {
                        Icon(Icons.Filled.Task, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Start Task")
                    }
                }
            }
        }
    }
}