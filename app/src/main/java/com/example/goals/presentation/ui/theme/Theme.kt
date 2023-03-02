package com.example.goals.presentation.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val MainColorPalette = lightColors(
    primary = GrayShadeDark,
    onPrimary = White,
    background = GrayShadeDark,
    onBackground = White,
    secondary = GrayShadeLight,
    secondaryVariant = Gray,
    onSecondary = Blue,
    surface = GrayShadeLight,
    onSurface = Color.Transparent,
    error = Color.Red
)

@Composable
fun GoalsTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = MainColorPalette,
        typography = CustomTypography,
        shapes = Shapes,
        content = content
    )
}
