package com.example.practice1.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice1.domain.uc.GetTodoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MainVMImp @Inject constructor(
    val getTodoListUseCase: GetTodoListUseCase
) : ViewModel(

) {
    private val _state = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val state  = _state.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _state.value = MainUiState.Loading
            val response = getTodoListUseCase().collect {
                try {
                    _state.value = MainUiState.Success(
                        it
                    )
                } catch (e: Exception) {
                    _state.value = MainUiState.Error
                }
            }
        }
    }


     fun userAction(userAction: UserAction) {
        when(userAction){
            is UserAction.LoadData -> getData()
            is UserAction.Retry -> getData()
            UserAction.Filter -> {}
            is UserAction.TodoItemClicked -> {
                // handled in the view layer
                throw NotImplementedError()
            }
        }
    }


}

sealed class UserAction {
    data object LoadData : UserAction()
    data object Retry : UserAction()
    data object Filter : UserAction()
    data class TodoItemClicked(val id: Int) : UserAction()
}

sealed interface MainUiState {
    data class Success(val data: List<Int>) : MainUiState
    data object Error : MainUiState
    data object Loading : MainUiState
}