package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SMSScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("SMS Tasks", style = MaterialTheme.typography.titleLarge) }
            )
        },
        // ✅ Removed activeRoute (no longer in your BottomNavigationBar)
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Your Daily SMS Tasks",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(10.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("SIM 1 – JIO 4G", fontWeight = FontWeight.Medium)
                    Text("Sent: 50 / 100", style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(8.dp))

                    Button(onClick = {
                        // TODO: Trigger task logic
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Send Task"
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Start Task")
                    }
                }
            }
        }
    }
}