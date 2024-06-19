package com.devx.matchmate.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDivider(height: Dp = 4.dp) {
    androidx.compose.material3.VerticalDivider(modifier = Modifier.height(height = height))
}

@Composable
fun HorizontalDivider(width: Dp = 4.dp) {
    androidx.compose.material3.HorizontalDivider(modifier = Modifier.width(width = width))
}
