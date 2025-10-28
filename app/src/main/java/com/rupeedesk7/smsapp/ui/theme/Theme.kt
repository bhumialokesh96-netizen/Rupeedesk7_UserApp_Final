package com.rupeedesk7.smsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.VerticalGradient
import androidx.compose.ui.unit.dp

private val LightColors = lightColors(
    primary = Primary,
    primaryVariant = PrimaryVariant,
    secondary = Secondary,
    background = Background,
    surface = Surface,
    error = Error,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColors = darkColors(
    primary = DarkPrimary,
    primaryVariant = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    error = Error,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

/** Gradient brush helper for top bars */
val TopBarGradient = Brush.verticalGradient(
    colors = listOf(Primary, PrimaryVariant),
    startY = 0f,
    endY = 300f,
    tileMode = TileMode.Clamp
)

@Composable
fun RupeedeskTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(colors = colors, typography = Typography(), shapes = Shapes(), content = content)
}