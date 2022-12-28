package com.example.goals.presentation.components

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val name: String,
    val route: String,
    @DrawableRes
    val iconId: Int,
    val badgeCount: Int = 0
) {
}