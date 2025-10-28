package com.rupeedesk7.smsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WithdrawScreen(navController: NavController) {
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Withdraw Balance", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Enter Amount (₹)") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            // Prototype withdraw logic
            println("Withdraw requested for ₹$amount")
            navController.popBackStack()
        }) {
            Text("Submit")
        }
    }
}
