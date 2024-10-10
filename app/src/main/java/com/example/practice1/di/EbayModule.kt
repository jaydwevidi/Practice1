package com.example.practice1.di

import com.example.practice1.data.remote.FetchTodoApi
import com.example.practice1.data.repository.TodoRepoImp
import com.example.practice1.domain.repository.TodoReository
import com.example.practice1.domain.uc.GetTodoDetailsUseCase
import com.example.practice1.domain.uc.GetTodoListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object EbayModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        val BASE_URL = "https://jsonplaceholder.typicode.com/"
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun providesEbayApi(retrofit: Retrofit): FetchTodoApi {
        return retrofit.create(FetchTodoApi::class.java)
    }

    @Provides
    fun providesTodoRepository(ebayApi: FetchTodoApi): TodoReository {
        return TodoRepoImp(ebayApi)
    }


    @Provides
    fun providesGetTodoListUseCase(todoRepository: TodoReository): GetTodoListUseCase {
        return GetTodoListUseCase(todoRepository)
    }

    @Provides
    fun providesGetTodoDetailsUseCase(todoRepository: TodoReository): GetTodoDetailsUseCase {
        return GetTodoDetailsUseCase(todoRepository)
    }
}