package com.example.practice1.data.remote

import com.example.practice1.data.models.ItemDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface FetchTodoApi {

    @GET("todos")
    suspend fun getTodoList(): List<ItemDTO>

    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): ItemDTO

}