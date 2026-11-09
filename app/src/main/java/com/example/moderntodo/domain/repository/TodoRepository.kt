package com.example.moderntodo.domain.repository

import com.example.moderntodo.domain.model.Category
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(
        query: String = "",
        category: Category? = null,
        priority: Priority? = null,
        hideCompleted: Boolean = false
    ): Flow<List<TodoItem>>

    suspend fun getTodoById(id: Long): TodoItem?

    suspend fun insertTodo(todo: TodoItem): Long

    suspend fun updateTodo(todo: TodoItem)

    suspend fun deleteTodo(todo: TodoItem)

    suspend fun toggleCompleted(id: Long, completed: Boolean)
}
