package com.example.moderntodo.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moderntodo.domain.model.Category
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.domain.model.TodoItem
import com.example.moderntodo.domain.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow(Category.ALL)
    private val _selectedPriority = MutableStateFlow<Priority?>(null)
    private val _hideCompleted = MutableStateFlow(false)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                _searchQuery,
                _selectedCategory,
                _selectedPriority,
                _hideCompleted
            ) { query, category, priority, hideCompleted ->
                FilterParams(query, category, priority, hideCompleted)
            }.flatMapLatest { params ->
                repository.getTodos(
                    query = params.query,
                    category = params.category,
                    priority = params.priority,
                    hideCompleted = params.hideCompleted
                )
            }.collect { todos ->
                val total = todos.size
                val completed = todos.count { it.isCompleted }
                _uiState.update { current ->
                    current.copy(
                        todos = todos,
                        searchQuery = _searchQuery.value,
                        selectedCategory = _selectedCategory.value,
                        selectedPriority = _selectedPriority.value,
                        hideCompleted = _hideCompleted.value,
                        completedCount = completed,
                        totalCount = total,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.SearchQueryChanged -> {
                _searchQuery.value = event.query
            }
            is HomeUiEvent.CategorySelected -> {
                _selectedCategory.value = event.category
            }
            is HomeUiEvent.PrioritySelected -> {
                _selectedPriority.value = event.priority
            }
            is HomeUiEvent.ToggleHideCompleted -> {
                _hideCompleted.value = event.hide
            }
            is HomeUiEvent.ToggleTodoCompleted -> {
                viewModelScope.launch {
                    repository.toggleCompleted(event.todo.id, event.completed)
                }
            }
            is HomeUiEvent.DeleteTodo -> {
                viewModelScope.launch {
                    repository.deleteTodo(event.todo)
                }
            }
            is HomeUiEvent.SaveTodo -> {
                viewModelScope.launch {
                    if (event.todo.id == 0L) {
                        repository.insertTodo(event.todo)
                    } else {
                        repository.updateTodo(event.todo)
                    }
                }
            }
        }
    }

    private data class FilterParams(
        val query: String,
        val category: Category,
        val priority: Priority?,
        val hideCompleted: Boolean
    )

    class Factory(private val repository: TodoRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}
