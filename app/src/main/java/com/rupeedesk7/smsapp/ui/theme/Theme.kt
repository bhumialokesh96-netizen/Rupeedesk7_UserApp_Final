package com.rupeedesk7.smsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

private val LightColors = lightColors(
    primary = Color(0xFF0B5FFF),
    primaryVariant = Color(0xFF0848CC),
    secondary = Color(0xFF00C2A8),
    background = Color(0xFFF7F9FF),
    surface = Color(0xFFFFFFFF),
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColors = darkColors(
    primary = Color(0xFF78A9FF),
    primaryVariant = Color(0xFF78A9FF),
    secondary = Color(0xFF63E6C0),
    background = Color(0xFF0B1220),
    surface = Color(0xFF0F1B2B),
    error = Color(0xFFB00020),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

val TopBarGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF0B5FFF), Color(0xFF0848CC)),
    startY = 0f,
    endY = 300f,
    tileMode = TileMode.Clamp
)

@Composable
fun RupeedeskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}