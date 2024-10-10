package com.example.practice1.domain.repository

import com.example.practice1.data.models.ItemDTO
import kotlinx.coroutines.flow.Flow

interface TodoReository {
    fun getTodoList() : Flow<List<Int>>
    fun getTodoById(id : Int) : Flow<ItemDTO>
}