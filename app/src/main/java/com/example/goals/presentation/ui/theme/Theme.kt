package com.example.goals.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val MainColorPalette = darkColors(
    primary = TextWhite,
    background = GrayShadeDark,
    onBackground = TextWhite,
    secondary = PurpleLight,
    surface = LightBlue,
    onSurface = GrayShadeDark
)

@Composable
fun GoalsTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = MainColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}