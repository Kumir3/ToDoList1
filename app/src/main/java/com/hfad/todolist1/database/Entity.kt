package com.hfad.todolist1.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class Entity(task: String) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0;
    val task: String = "";
}