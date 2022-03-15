package com.example.android_todo_lab

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.android_todo_lab.databinding.CreateTodoViewBinding
import android.text.TextWatcher

class CreateToDoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = CreateTodoViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        val titleEditText = viewBinding.ToDoTitleEditText
        val contentEditText = viewBinding.ToDoContentEditTextText

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

                viewBinding.createToDoButton.isEnabled = (titleError.isEmpty() && contentError.isEmpty())
            }
        }

        titleEditText.addTextChangedListener(textWatcher)
        contentEditText.addTextChangedListener(textWatcher)

        viewBinding.createToDoButton.setOnClickListener{
            val title = titleEditText.editableText.toString()
            val content = contentEditText.editableText.toString()

            val newToDoId = toDoRepository.addToDo(title, content)

            val intent = Intent(this, ViewToDoDetailActivity::class.java)
            intent.putExtra(ViewToDoDetailActivity.EXTRA_TODO_ID, newToDoId)
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