package com.example.android_todo_lab

data class ToDo(
    val id: Int,
    var title: String,
    var content: String
) {
    override fun toString() = title
}
