package com.rupeedesk7.smsapp.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun LoginScreen(onLogin:()->Unit){
  Column(modifier=Modifier.fillMaxSize().padding(16.dp)){
    Text("Login (phone only)"); Spacer(Modifier.height(8.dp))
    var phone by remember{ mutableStateOf("") }
    OutlinedTextField(value=phone,onValueChange={phone=it},label={ Text("Phone") })
    Spacer(Modifier.height(8.dp))
    Button(onClick=onLogin){ Text("Continue") }
  }
}
