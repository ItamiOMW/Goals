package com.example.goals.presentation.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.goals.presentation.ui.theme.TextWhite


@Composable
fun CustomTextField(
    state: TextFieldState,
    color: Color,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    textStyle: TextStyle = TextStyle()
) {
    BasicTextField(
        value = state.text,
        onValueChange = { string ->
            onValueChange(string)
        },
        textStyle = textStyle,
        singleLine = singleLine,
        modifier = modifier
    ) {
        it()
    }
    Divider(
        color = if (state.textError != null) Color.Red else TextWhite,
    )
}