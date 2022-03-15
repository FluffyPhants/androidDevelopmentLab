package com.example.android_todo_lab

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.android_todo_lab.databinding.DetailedTaskViewBinding

class ViewToDoDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TODO_ID = "TODO_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = DetailedTaskViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        val id = intent.getIntExtra(EXTRA_TODO_ID, 0)

        if(id == 0) {
            finish()
        }

        val title = toDoRepository.getToDoById(id)?.title
        val content = toDoRepository.getToDoById(id)?.content

        viewBinding.taskTitle.text = title
        viewBinding.taskContent.text = content

        viewBinding.updateToDoButton.setOnClickListener{

            val intent = Intent(this, UpdateToDoActivity::class.java)
            intent.putExtra(UpdateToDoActivity.EXTRA_TODO_ID, id)
            startActivity(intent)

            finish()
        }
        viewBinding.deleteToDoButton.setOnClickListener{

            AlertDialog.Builder(this)
                .setTitle("Delete ToDo")
                .setMessage("Do you really want to delete it?")
                .setPositiveButton(
                    "Yes"
                ) { dialog, whichButton ->
                    // Delete it.
                    toDoRepository.deleteToDoById(id)
                    finish()
                }.setNegativeButton(
                    "No"
                ) { dialog, whichButton ->
                    // Do not delete it.
                }.show()
        }
    }
}