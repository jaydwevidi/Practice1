package com.example.practice1.domain.models

data class TodoItem(
    val completed: Boolean,
    val id: Int,
    val title: String?,
    val userId: Int
)
