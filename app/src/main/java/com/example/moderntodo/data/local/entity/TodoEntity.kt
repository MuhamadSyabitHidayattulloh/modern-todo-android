package com.example.moderntodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moderntodo.domain.model.Category
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.domain.model.TodoItem

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val isCompleted: Boolean,
    val createdAt: Long,
    val dueDate: Long?
) {
    fun toDomain(): TodoItem {
        return TodoItem(
            id = id,
            title = title,
            description = description,
            category = Category.fromString(category),
            priority = Priority.fromString(priority),
            isCompleted = isCompleted,
            createdAt = createdAt,
            dueDate = dueDate
        )
    }

    companion object {
        fun fromDomain(todo: TodoItem): TodoEntity {
            return TodoEntity(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                category = todo.category.name,
                priority = todo.priority.name,
                isCompleted = todo.isCompleted,
                createdAt = todo.createdAt,
                dueDate = todo.dueDate
            )
        }
    }
}
