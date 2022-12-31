package com.example.goals.presentation.components

import androidx.annotation.DrawableRes

data class NavigationItem(
    val name: String,
    val route: String,
    @DrawableRes
    val iconId: Int,
    val badgeCount: Int = 0
) {
}