package com.example.moderntodo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moderntodo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("""
        SELECT * FROM todos 
        WHERE (:query = '' OR title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        AND (:category IS NULL OR category = :category)
        AND (:priority IS NULL OR priority = :priority)
        AND (:hideCompleted = 0 OR isCompleted = 0)
        ORDER BY isCompleted ASC, 
                 CASE priority 
                     WHEN 'HIGH' THEN 1 
                     WHEN 'MEDIUM' THEN 2 
                     WHEN 'LOW' THEN 3 
                     ELSE 4 
                 END ASC, 
                 createdAt DESC
    """)
    fun getFilteredTodos(
        query: String,
        category: String?,
        priority: String?,
        hideCompleted: Boolean
    ): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id LIMIT 1")
    suspend fun getTodoById(id: Long): TodoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("UPDATE todos SET isCompleted = :completed WHERE id = :id")
    suspend fun updateCompleted(id: Long, completed: Boolean)
}
