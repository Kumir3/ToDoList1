package com.hfad.todolist1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Entity>

    @Insert
    fun insertTask(task: Entity)
}