package com.example.shanti.common

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationDrawerItemInfo(
    val name: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)