package com.example.moderntodo.domain.model

data class TodoItem(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val category: Category = Category.PERSONAL,
    val priority: Priority = Priority.MEDIUM,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val dueDate: Long? = null
)
