package com.example.android_todo_lab

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.android_todo_lab.databinding.UpdateTodoViewBinding

class UpdateToDoActivity : AppCompatActivity(){
    companion object {
        const val EXTRA_TODO_ID = "TODO_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = UpdateTodoViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        val id = intent.getIntExtra(EXTRA_TODO_ID, 0)

        if(id == 0) {
            finish()
        }

        val todo = toDoRepository.getToDoById(id)
        val titleEditText = viewBinding.ToDoTitleEditText
        val contentEditText = viewBinding.ToDoContentEditTextText

        titleEditText.setText(todo?.title)
        contentEditText.setText(todo?.content)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val titleError = validateTitle(titleEditText.editableText.toString())
                val contentError = validateContent(contentEditText.editableText.toString())

                if(titleError.isNotEmpty()){
                    viewBinding.TitleInputLayout.error = titleError[0]
                }
                else {
                    viewBinding.TitleInputLayout.error = null
                }
                if(contentError.isNotEmpty()){
                    viewBinding.ContentInputLayout.error = contentError[0]
                }
                else {
                    viewBinding.ContentInputLayout.error = null
                }

                viewBinding.updateToDoButton.isEnabled = (titleError.isEmpty() && contentError.isEmpty())
            }
        }

        titleEditText.addTextChangedListener(textWatcher)
        contentEditText.addTextChangedListener(textWatcher)

        viewBinding.updateToDoButton.setOnClickListener{

            val title = titleEditText.editableText.toString()
            val content = contentEditText.editableText.toString()

            toDoRepository.updateToDoById(id, title, content)

            val intent = Intent(this, ViewToDoDetailActivity::class.java)
            intent.putExtra(ViewToDoDetailActivity.EXTRA_TODO_ID, id)
            startActivity(intent)

            finish()
        }
    }

    fun validateTitle(input : String) : List<String>{
        val errorList = mutableListOf<String>()

        when {
            input.length < 3 -> {
                errorList.add(getString(R.string.titleToShort))
            }
            input.length > 30 -> {
                errorList.add(getString(R.string.titleToLong))
            }
            input.isEmpty() -> {
                errorList.add(getString(R.string.titleMissing))
            }
        }

        return errorList
    }

    fun validateContent(input : String) : List<String>{
        val errorList = mutableListOf<String>()

        when {
            input.length < 5 -> {
                errorList.add(getString(R.string.contentToShort))
            }
            input.length > 150 -> {
                errorList.add(getString(R.string.contentToLong))
            }
            input.isEmpty() -> {
                errorList.add(getString(R.string.contentMissing))
            }
        }

        return errorList
    }
}

