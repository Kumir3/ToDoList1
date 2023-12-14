package com.hfad.todolist1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.hfad.todolist1.database.Database
import com.hfad.todolist1.database.Entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var taskDatabase: Database
    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button
    private lateinit var listViewTasks: ListView
    private lateinit var tasks: MutableList<String>
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTask = findViewById(R.id.editTextTask)
        buttonAdd = findViewById(R.id.buttonAdd)
        listViewTasks = findViewById(R.id.listViewTasks)

        val taskAdapter = Adapter(tasks)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Замените на ваш идентификатор RecyclerView
        recyclerView.adapter = taskAdapter

        tasks = mutableListOf()
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
        listViewTasks.adapter = arrayAdapter

        buttonAdd.setOnClickListener { addTask() }

        taskDatabase = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "task-database"
        ).build()

        buttonAdd.setOnClickListener { addTask() }

        loadTasks()
    }

    private fun addTask() {
        val task = editTextTask.text.toString()
        if (task.isNotEmpty()) {
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    taskDatabase.taskDao().insertTask(Entity(task = task))
                }
                withContext(Dispatchers.Main) {
                    tasks.add(task)
                    arrayAdapter.notifyDataSetChanged()
                    editTextTask.text.clear()
                }
            }
        }
    }

    private fun loadTasks() {
        GlobalScope.launch {
            val loadedTasks = withContext(Dispatchers.IO) {
                taskDatabase.taskDao().getAllTasks().map { it.task }
            }
            withContext(Dispatchers.Main) {
                tasks.addAll(loadedTasks)
                arrayAdapter.notifyDataSetChanged()
            }
        }
    }
}