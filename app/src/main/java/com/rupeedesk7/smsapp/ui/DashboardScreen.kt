package com.rupeedesk7.smsapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.rupeedesk7.smsapp.data.UserModel
import com.rupeedesk7.smsapp.worker.SmsWorker
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val ctx = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    var phone by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf(0.0) }
    var dailySent by remember { mutableStateOf(0) }
    var dailyLimit by remember { mutableStateOf(50) }
    var spins by remember { mutableStateOf(0) }
    var simList by remember { mutableStateOf(emptyList<SubscriptionInfo>()) }
    var selectedSim by remember { mutableStateOf(-1) }

    // 🔹 Load user info once
    LaunchedEffect(Unit) {
        try {
            val snap = db.collection("users").limit(1).get().await()
            if (!snap.isEmpty) {
                val doc = snap.documents[0]
                val u = doc.toObject<UserModel>()
                phone = u?.phone ?: doc.id
                name = u?.name ?: ""
                balance = u?.balance ?: 0.0
                dailySent = (u?.dailySent ?: 0L).toInt()
                dailyLimit = (u?.dailyLimit ?: 50L).toInt()
                spins = (u?.spins ?: 0L).toInt()
                selectedSim = (u?.simId ?: -1L).toInt()
            }
        } catch (e: Exception) {
            Toast.makeText(ctx, "Error loading user: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dashboard", style = MaterialTheme.typography.titleLarge) }
            )
        },
        bottomBar = {
            // ✅ fixed parameter name: only navController needed
            BottomNavigationBar(navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Welcome, $name 👋", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text("📱 Phone: $phone", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("💰 Balance: ₹${String.format("%.2f", balance)}", style = MaterialTheme.typography.bodyLarge)
            Text("📤 Sent today: $dailySent / $dailyLimit", style = MaterialTheme.typography.bodyLarge)

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val work = OneTimeWorkRequestBuilder<SmsWorker>().build()
                    WorkManager.getInstance(ctx)
                        .enqueueUniqueWork("send_one_sms", ExistingWorkPolicy.APPEND_OR_REPLACE, work)
                    Toast.makeText(ctx, "SMS scheduled successfully ✅", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Sending (Auto)")
            }

            Spacer(Modifier.height(20.dp))

            // 🔸 Navigation buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate("spin") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🎡 Spin ($spins)")
                }

                Button(
                    onClick = { navController.navigate("profile") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("👤 Profile")
                }

                Button(
                    onClick = { navController.navigate("withdraw") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("💵 Withdraw")
                }
            }
        }
    }
}