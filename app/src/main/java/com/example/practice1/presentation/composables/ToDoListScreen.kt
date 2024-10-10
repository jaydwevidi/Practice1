package com.example.practice1.presentation.composables

// presentation/composable/TodoListScreen.kt


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.practice1.presentation.vm.MainUiState
import com.example.practice1.presentation.vm.MainVMImp
import com.example.practice1.presentation.vm.UserAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    navController: NavController,
    viewModel : MainVMImp = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.userAction(UserAction.LoadData)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState) {
                is MainUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is MainUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("An error occurred.")
                        Button(onClick = { viewModel.userAction(UserAction.Retry) }) {
                            Text("Retry")
                        }
                    }
                }
                is MainUiState.Success -> {
                    val todos = (uiState as MainUiState.Success).data
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(todos) { todo ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val id : Int = todo
                                        navController.navigate("todoDetails/$id")
                                    }
                            ) {
                                ListItem(
                                    headlineContent = { Text("title"+todo) },
                                    supportingContent = { Text("User ID: ${todo}") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}