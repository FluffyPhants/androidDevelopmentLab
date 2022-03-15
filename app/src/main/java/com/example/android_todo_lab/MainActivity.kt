package com.example.android_todo_lab

import android.content.Intent
import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.android_todo_lab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var adapter: ArrayAdapter<ToDo>? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = ActivityMainBinding.inflate(layoutInflater)

        adapter = ArrayAdapter<ToDo> (
            this,
            R.layout.task_view,
            R.id.taskTitleTextView,
            toDoRepository.getAllToDos()
        )

        viewBinding.taskListView.adapter = adapter

        viewBinding.taskListView.setOnItemClickListener {parent, view, position, id ->

            val actualId = (id + 1).toInt()

            val intent = Intent(this, ViewToDoDetailActivity::class.java)
            intent.putExtra(ViewToDoDetailActivity.EXTRA_TODO_ID, actualId)
            startActivity(intent)
        }

        viewBinding.addTaskButton.setOnClickListener{
            val intent = Intent(this, CreateToDoActivity::class.java)
            startActivity(intent)
        }

        setContentView(viewBinding.root)
    }

    override fun onStart() {
        super.onStart()
        adapter?.notifyDataSetChanged()
    }
}