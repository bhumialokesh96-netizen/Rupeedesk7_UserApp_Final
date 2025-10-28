package com.rupeedesk7.smsapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.rupeedesk7.smsapp.data.UserModel
import com.rupeedesk7.smsapp.ui.components.TopBarWithGradient
import com.rupeedesk7.smsapp.ui.theme.RupeedeskTheme
import kotlinx.coroutines.tasks.await

@Composable
fun DashboardScreen(navController: NavController) {
    RupeedeskTheme {
        val ctx = LocalContext.current
        val db = FirebaseFirestore.getInstance()

        var phone by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("User") }
        var balance by remember { mutableStateOf(0.0) }
        var dailySent by remember { mutableStateOf(0) }
        var dailyLimit by remember { mutableStateOf(50) }
        var spins by remember { mutableStateOf(0) }
        var simList by remember { mutableStateOf(listOf<SubscriptionInfo>()) }
        var selectedSim by remember { mutableStateOf(-1) }
        var smsPermissionGranted by remember { mutableStateOf(false) }

        val smsLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            smsPermissionGranted = granted
            if (!granted) Toast.makeText(ctx, "SEND_SMS denied", Toast.LENGTH_SHORT).show()
        }

        val phoneLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (!granted) Toast.makeText(ctx, "READ_PHONE_STATE denied", Toast.LENGTH_SHORT).show()
        }

        LaunchedEffect(Unit) {
            smsPermissionGranted = ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED

            val snap = db.collection("users").limit(1).get().await()
            if (!snap.isEmpty) {
                val doc = snap.documents[0]
                val u = doc.toObject<UserModel>()
                phone = u?.phone ?: doc.id
                name = u?.name ?: "User"
                balance = u?.balance ?: 0.0
                dailySent = (u?.dailySent ?: 0L).toInt()
                dailyLimit = (u?.dailyLimit ?: 50L).toInt()
                spins = (u?.spins ?: 0L).toInt()
                selectedSim = (u?.simId ?: -1L).toInt()
            }

            if (ContextCompat.checkSelfPermission(
                    ctx, Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val sm = ctx.getSystemService(SubscriptionManager::class.java)
                simList = sm?.activeSubscriptionInfoList ?: emptyList()
            }
        }

        Scaffold(
            topBar = {
                TopBarWithGradient(title = "Rupeedesk") {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    Toast.makeText(ctx, "Coming soon", Toast.LENGTH_SHORT).show()
                }) { Icon(Icons.Default.Add, contentDescription = null) }
            },
            bottomBar = {
                BottomAppBar {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(onClick = {}) { Icon(Icons.Default.Send, null) }
                        IconButton(onClick = { navController.navigate("profile") }) {
                            Icon(Icons.Default.AccountCircle, null)
                        }
                    }
                }
            }
        ) { inner ->
            Column(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
            ) {
                Card(elevation = 6.dp, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Welcome back,", style = MaterialTheme.typography.body2)
                        Text(name, style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold))
                        Spacer(Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text("Balance", style = MaterialTheme.typography.caption)
                                Text("₹${"%.2f".format(balance)}", fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text("Today", style = MaterialTheme.typography.caption)
                                Text("$dailySent / $dailyLimit", fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text("Spins", style = MaterialTheme.typography.caption)
                                Text("$spins", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Card(elevation = 3.dp, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Select SIM", style = MaterialTheme.typography.subtitle2)
                        if (simList.isEmpty()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text(
                                    "No SIM or permission missing",
                                    style = MaterialTheme.typography.body2
                                )
                                Spacer(Modifier.width(8.dp))
                                TextButton(onClick = {
                                    phoneLauncher.launch(Manifest.permission.READ_PHONE_STATE)
                                }) { Text("Grant") }
                            }
                        } else {
                            simList.forEach { s ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedSim = s.subscriptionId
                                            if (phone.isNotBlank())
                                                db.collection("users").document(phone)
                                                    .update("simId", selectedSim)
                                            Toast.makeText(ctx, "Selected ${s.carrierName}", Toast.LENGTH_SHORT).show()
                                        }
                                        .padding(vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(s.carrierName.toString(), fontWeight = FontWeight.SemiBold)
                                        Text(
                                            s.number ?: "hidden",
                                            style = MaterialTheme.typography.caption,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (selectedSim == s.subscriptionId)
                                        Text("Active", color = MaterialTheme.colors.secondary)
                                }
                                Divider()
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text("Quick actions", style = MaterialTheme.typography.subtitle1)
                LazyColumn {
                    items(listOf("Send now", "Add message", "Referrals", "Daily check-in")) { item ->
                        Card(elevation = 2.dp, modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item, fontWeight = FontWeight.Medium)
                                    Text("Tap to open", style = MaterialTheme.typography.caption)
                                }
                                Button(onClick = {
                                    when (item) {
                                        "Send now" ->
                                            if (!smsPermissionGranted)
                                                smsLauncher.launch(Manifest.permission.SEND_SMS)
                                            else
                                                Toast.makeText(ctx, "Scheduling...", Toast.LENGTH_SHORT).show()
                                        "Add message" -> navController.navigate("compose")
                                        "Referrals" -> navController.navigate("refer")
                                        "Daily check-in" -> navController.navigate("spin")
                                    }
                                }) { Text("Open") }
                            }
                        }
                    }
                }
            }
        }
    }
}