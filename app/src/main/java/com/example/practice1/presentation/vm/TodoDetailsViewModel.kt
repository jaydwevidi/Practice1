package com.example.practice1.presentation.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice1.domain.models.TodoItem
import com.example.practice1.domain.uc.GetTodoDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(
    private val getTodoDetailsUseCase: GetTodoDetailsUseCase
) : ViewModel() {



    private val _state = MutableStateFlow<TodoDetailsUiState>(TodoDetailsUiState.Loading)
    val state: StateFlow<TodoDetailsUiState> = _state.asStateFlow()


    fun userAction(action: TodoDetailsAction) {
        when (action) {
            is TodoDetailsAction.LoadData -> getData(action.id)
            is TodoDetailsAction.Retry -> getData(action.id)
        }
    }

    private fun getData(id: Int) {
        println("todoId: inside getdata  $id")
        viewModelScope.launch {
            _state.value = TodoDetailsUiState.Loading
            getTodoDetailsUseCase(id)
                .catch { _state.value = TodoDetailsUiState.Error }
                .collect { item ->
                    _state.value = TodoDetailsUiState.Success(item)
                }
        }
    }
}

sealed interface TodoDetailsUiState {
    data class Success(val data: TodoItem) : TodoDetailsUiState
    object Error : TodoDetailsUiState
    object Loading : TodoDetailsUiState
}


sealed class TodoDetailsAction {
    data class LoadData(val id: Int) : TodoDetailsAction()
    data class Retry(val id: Int) : TodoDetailsAction()
}