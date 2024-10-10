package com.example.practice1.data.repository

import com.example.practice1.data.models.ItemDTO
import com.example.practice1.data.remote.FetchTodoApi
import com.example.practice1.domain.repository.TodoReository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class TodoRepoImp
    @Inject constructor(
        private val fetchTodoApi: FetchTodoApi
    ) : TodoReository {
    override fun getTodoList(): Flow<List<Int>> {
        return flow {
            val returnString : List<Int> = fetchTodoApi.getTodoList().map {
                it.id
            }
            emit(value = returnString)
        }
    }

    override fun getTodoById(id: Int): Flow<ItemDTO> {
       return flow {
           emit(fetchTodoApi.getTodoById(id))
       }.transform { it : ItemDTO ->
           emit(it)
       }
    }

}