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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.rupeedesk7.smsapp.data.UserModel
import com.rupeedesk7.smsapp.worker.SmsWorker
import kotlinx.coroutines.tasks.await
import androidx.core.content.ContextCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

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
    var smsPermissionGranted by remember { mutableStateOf(false) }

    // Permission launchers
    val smsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        smsPermissionGranted = granted
        if (!granted) {
            Toast.makeText(ctx, "SEND_SMS permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    val phoneLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(ctx, "Phone permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        smsPermissionGranted = ContextCompat.checkSelfPermission(
            ctx,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED

        // Load one user (prototype)
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

        // Request SIM list if phone permission granted
        if (ContextCompat.checkSelfPermission(
                ctx,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val sm = ctx.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            simList = sm.activeSubscriptionInfoList ?: emptyList()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Welcome, $name", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(8.dp))
        Text("Balance: ₹${String.format("%.2f", balance)}")
        Text("Sent today: $dailySent / $dailyLimit")
        Spacer(Modifier.height(12.dp))

        // Permission Buttons
        Row {
            Button(onClick = {
                if (!smsPermissionGranted) {
                    smsLauncher.launch(Manifest.permission.SEND_SMS)
                } else {
                    Toast.makeText(ctx, "SEND_SMS already granted", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Grant SEND_SMS")
            }

            Spacer(Modifier.width(8.dp))

            Button(onClick = {
                phoneLauncher.launch(Manifest.permission.READ_PHONE_STATE)
            }) {
                Text("Grant READ_PHONE_STATE")
            }
        }

        Spacer(Modifier.height(12.dp))
        Text("Select SIM to use:")
        if (simList.isEmpty()) {
            Text("No SIM info available or permission not granted.")
        } else {
            simList.forEach { s ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            selectedSim = s.subscriptionId
                            if (phone.isNotBlank()) {
                                db.collection("users").document(phone).update("simId", selectedSim)
                            }
                            Toast.makeText(
                                ctx,
                                "Selected SIM: ${s.carrierName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                ) {
                    // ✅ Fixed the string template quotes issue here
                    Text(text = "${s.carrierName} (${s.number ?: "hidden"})")
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Worker trigger
        Button(onClick = {
            if (!smsPermissionGranted) {
                Toast.makeText(ctx, "Please grant SEND_SMS first", Toast.LENGTH_LONG).show()
                return@Button
            }

            val work = OneTimeWorkRequestBuilder<SmsWorker>().build()
            WorkManager.getInstance(ctx).enqueueUniqueWork(
                "rupeedesk7_send_one",
                ExistingWorkPolicy.REPLACE,
                work
            )
            Toast.makeText(ctx, "Scheduled SMS worker", Toast.LENGTH_SHORT).show()
        }) {
            Text("Start Sending (Auto)")
        }

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { navController.navigate("spin") }) {
                Text("Spin Wheel ($spins)")
            }
            Button(onClick = { navController.navigate("profile") }) {
                Text("Profile")
            }
            Button(onClick = { navController.navigate("withdraw") }) {
                Text("Withdraw")
            }
        }
    }
}
