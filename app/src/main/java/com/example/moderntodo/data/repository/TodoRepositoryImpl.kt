package com.example.moderntodo.data.repository

import com.example.moderntodo.data.local.TodoDao
import com.example.moderntodo.data.local.entity.TodoEntity
import com.example.moderntodo.domain.model.Category
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.domain.model.TodoItem
import com.example.moderntodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun getTodos(
        query: String,
        category: Category?,
        priority: Priority?,
        hideCompleted: Boolean
    ): Flow<List<TodoItem>> {
        val categoryFilter = if (category == Category.ALL || category == null) null else category.name
        val priorityFilter = priority?.name
        return todoDao.getFilteredTodos(
            query = query.trim(),
            category = categoryFilter,
            priority = priorityFilter,
            hideCompleted = hideCompleted
        ).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTodoById(id: Long): TodoItem? {
        return todoDao.getTodoById(id)?.toDomain()
    }

    override suspend fun insertTodo(todo: TodoItem): Long {
        return todoDao.insertTodo(TodoEntity.fromDomain(todo))
    }

    override suspend fun updateTodo(todo: TodoItem) {
        todoDao.updateTodo(TodoEntity.fromDomain(todo))
    }

    override suspend fun deleteTodo(todo: TodoItem) {
        todoDao.deleteTodo(TodoEntity.fromDomain(todo))
    }

    override suspend fun toggleCompleted(id: Long, completed: Boolean) {
        todoDao.updateCompleted(id, completed)
    }
}
