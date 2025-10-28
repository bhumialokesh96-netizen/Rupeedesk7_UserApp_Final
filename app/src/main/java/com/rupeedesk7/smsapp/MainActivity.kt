package com.rupeedesk7.smsapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.rupeedesk7.smsapp.ui.AppNavHost
import com.rupeedesk7.smsapp.ui.AppTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent { AppTheme { AppNavHost() } }
    }
}
