package com.example.moderntodo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.ui.theme.PriorityHigh
import com.example.moderntodo.ui.theme.PriorityHighContainer
import com.example.moderntodo.ui.theme.PriorityLow
import com.example.moderntodo.ui.theme.PriorityLowContainer
import com.example.moderntodo.ui.theme.PriorityMedium
import com.example.moderntodo.ui.theme.PriorityMediumContainer

@Composable
fun PriorityBadge(
    priority: Priority,
    modifier: Modifier = Modifier
) {
    val (containerColor, contentColor) = when (priority) {
        Priority.HIGH -> PriorityHighContainer to PriorityHigh
        Priority.MEDIUM -> PriorityMediumContainer to PriorityMedium
        Priority.LOW -> PriorityLowContainer to PriorityLow
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(containerColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = priority.displayName,
            color = contentColor,
            fontSize = 11.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
