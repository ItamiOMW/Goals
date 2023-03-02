package com.example.goals.presentation.utils

import androidx.compose.runtime.Composable

data class TabItem(
    val title: String,
    val iconResId: Int,
    val screen: @Composable () -> Unit
)
