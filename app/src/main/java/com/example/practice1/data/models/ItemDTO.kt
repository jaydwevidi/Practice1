package com.example.practice1.data.models

data class ItemDTO(
    val completed: Boolean,
    val id: Int,
    val title: String?,
    val userId: Int
) {
    fun toItem(): TodoItem =  TodoItem(
            completed = completed,
            id = id,
            title = title,
            userId = userId
        )

}