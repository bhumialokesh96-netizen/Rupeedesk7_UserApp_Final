package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.layout.*
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
        bottomBar = { BottomNavigationBar(navController = navController, activeRoute = "sms") },
        topBar = { CenterAlignedTopAppBar(title = { Text("SMS Tasks") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Your Daily SMS Tasks", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(16.dp)) {
                    Text("SIM 1 – JIO 4G", fontWeight = FontWeight.Medium)
                    Text("Sent: 50 / 100")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { /* trigger task */ }) {
                        Icon(Icons.Filled.Send, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Start Task")
                    }
                }
            }
        }
    }
}