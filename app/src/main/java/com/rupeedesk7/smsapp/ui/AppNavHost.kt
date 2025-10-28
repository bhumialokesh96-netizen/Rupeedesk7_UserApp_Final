package com.rupeedesk7.smsapp.ui
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
@Composable fun AppNavHost(){
  val nav = rememberNavController()
  NavHost(navController=nav, startDestination="login"){
    composable("login"){ LoginScreen(onLogin={ nav.navigate("dashboard") }) }
    composable("dashboard"){ DashboardScreen(nav) }
    composable("profile"){ ProfileScreen() }
    composable("withdraw"){ WithdrawScreen(nav) }
    composable("spin"){ SpinScreen() }
  }
}
