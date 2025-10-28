package com.rupeedesk7.smsapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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

    // permission launcher for READ_PHONE_STATE (for sim list)
    val phoneLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            Toast.makeText(ctx, "Phone permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(ctx, "Phone permission required to see SIMs", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        // load basic user doc (prototype)
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
        } catch (ex: Exception) {
            // ignore for prototype
        }

        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val sm = ctx.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as? SubscriptionManager
            simList = sm?.activeSubscriptionInfoList ?: emptyList()
        }
    }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Dashboard") }) },
        bottomBar = { BottomNavigationBar(navController = navController, activeRoute = "dashboard") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Welcome, $name", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text("Balance: ₹${String.format("%.2f", balance)}")
            Text("Sent today: $dailySent / $dailyLimit")
            Spacer(Modifier.height(12.dp))

            Text("Select SIM to use:")
            if (simList.isEmpty()) {
                Row {
                    Text("No SIM info or permission missing.")
                    Spacer(Modifier.width(8.dp))
                    Text("Grant", modifier = Modifier
                        .clickable { phoneLauncher.launch(Manifest.permission.READ_PHONE_STATE) }
                        .padding(4.dp), style = MaterialTheme.typography.labelLarge)
                }
            } else {
                simList.forEach { s ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .clickable {
                            selectedSim = s.subscriptionId
                            if (phone.isNotBlank()) {
                                db.collection("users").document(phone).update("simId", selectedSim)
                            }
                            Toast.makeText(ctx, "Selected SIM: ${s.carrierName}", Toast.LENGTH_SHORT).show()
                        }) {
                        Text("${s.carrierName} (${s.number ?: "hidden"})")
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                // schedule WorkManager task as a one-off
                val work = OneTimeWorkRequestBuilder<SmsWorker>().build()
                WorkManager.getInstance(ctx).enqueueUniqueWork("rupeedesk_send_one", ExistingWorkPolicy.APPEND_OR_REPLACE, work)
                Toast.makeText(ctx, "SMS job scheduled", Toast.LENGTH_SHORT).show()
            }) {
                Text("Start Sending (Auto)")
            }

            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { navController.navigate("spin") }) { Text("Spin ($spins)") }
                Button(onClick = { navController.navigate("profile") }) { Text("Profile") }
                Button(onClick = { navController.navigate("withdraw") }) { Text("Withdraw") }
            }
        }
    }
}