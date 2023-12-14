package com.hfad.todolist1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val tasks: MutableList<String>) : RecyclerView.Adapter<Adapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTextView: TextView = itemView.findViewById(R.id.taskTextView) // Замените на ваш реальный идентификатор
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main, parent, false) // Замените на ваш макет элемента списка
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.taskTextView.text = currentTask.task
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}