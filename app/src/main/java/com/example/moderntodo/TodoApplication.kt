package com.example.moderntodo

import android.app.Application
import com.example.moderntodo.data.local.TodoDatabase
import com.example.moderntodo.data.repository.TodoRepositoryImpl
import com.example.moderntodo.domain.repository.TodoRepository

class TodoApplication : Application() {

    lateinit var repository: TodoRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = TodoDatabase.getInstance(this)
        repository = TodoRepositoryImpl(database.todoDao)
    }
}
