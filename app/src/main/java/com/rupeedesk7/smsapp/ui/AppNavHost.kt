package com.rupeedesk7.smsapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rupeedesk7.smsapp.ui.components.BottomNavigationBar
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("withdraw") { WithdrawScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("task") { TaskScreen(navController) }
        composable("spin") { SpinWheelScreen(navController) }
        composable("sms") { SMSScreen(navController) }
    }
}