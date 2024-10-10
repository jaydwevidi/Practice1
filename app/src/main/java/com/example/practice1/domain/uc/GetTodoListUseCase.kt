package com.example.practice1.domain.uc

import com.example.practice1.domain.repository.TodoReository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val todoRepository: TodoReository
) {
    operator fun invoke() : Flow<List<Int>> = todoRepository.getTodoList()
}
