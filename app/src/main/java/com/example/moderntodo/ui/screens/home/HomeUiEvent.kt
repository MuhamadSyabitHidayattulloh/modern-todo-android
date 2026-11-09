package com.example.moderntodo.ui.screens.home

import com.example.moderntodo.domain.model.Category
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.domain.model.TodoItem

sealed interface HomeUiEvent {
    data class SearchQueryChanged(val query: String) : HomeUiEvent
    data class CategorySelected(val category: Category) : HomeUiEvent
    data class PrioritySelected(val priority: Priority?) : HomeUiEvent
    data class ToggleHideCompleted(val hide: Boolean) : HomeUiEvent
    data class ToggleTodoCompleted(val todo: TodoItem, val completed: Boolean) : HomeUiEvent
    data class DeleteTodo(val todo: TodoItem) : HomeUiEvent
    data class SaveTodo(val todo: TodoItem) : HomeUiEvent
}
