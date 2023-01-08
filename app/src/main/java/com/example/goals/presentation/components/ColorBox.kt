package com.example.goals.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.goals.presentation.ui.theme.TextWhite


@Composable
fun ColorBox(
    size: Float = 50f,
    shape: Shape = CircleShape,
    color: Color,
    borderColor: Color = TextWhite,
    onColorBoxClick: (Color) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(size.dp)
            .shadow(15.dp, shape)
            .clip(shape)
            .background(color)
            .border(
                width = 5.dp,
                color = borderColor,
                shape = shape,
            )
            .clickable {
                onColorBoxClick(color)
            }
    )
}