package com.example.moderntodo.ui.screens.home

import com.example.moderntodo.domain.model.Category
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.domain.model.TodoItem

data class HomeUiState(
    val todos: List<TodoItem> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: Category = Category.ALL,
    val selectedPriority: Priority? = null,
    val hideCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val completedCount: Int = 0,
    val totalCount: Int = 0
) {
    val progressPercentage: Float
        get() = if (totalCount == 0) 0f else completedCount.toFloat() / totalCount.toFloat()
}
