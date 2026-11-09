package com.example.moderntodo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moderntodo.data.local.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    abstract val todoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "moderntodo.db"

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                 .build()
                 .also { INSTANCE = it }
            }
        }
    }
}
