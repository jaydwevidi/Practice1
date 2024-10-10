package com.example.practice1.domain.uc

import com.example.practice1.domain.repository.TodoReository
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetTodoDetailsUseCase
    @Inject constructor(
    private val repository: TodoReository
) {
        operator fun invoke(id : Int) = repository.getTodoById(id).transform {
            emit(it.toItem())
        }
}