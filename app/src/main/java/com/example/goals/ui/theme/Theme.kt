package com.example.goals.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MainColorPalette = darkColors(
    primary = GrayShade,
    primaryVariant = GrayShadeLight,
    secondary = PurpleLight,
    onPrimary = TextWhite,

)


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */


@Composable
fun GoalsTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = MainColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}