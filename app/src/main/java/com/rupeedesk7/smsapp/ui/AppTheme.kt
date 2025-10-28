package com.rupeedesk7.smsapp.ui
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val Palette = darkColors(primary = Color(0xFF1F8A70))
@Composable fun AppTheme(content: @Composable ()->Unit) { MaterialTheme(colors=Palette){ content() } }
