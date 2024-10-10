package com.example.practice1.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practice1.presentation.vm.TodoDetailsAction
import com.example.practice1.presentation.vm.TodoDetailsUiState
import com.example.practice1.presentation.vm.TodoDetailsViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailsScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    viewModel: TodoDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val todoId = backStackEntry.arguments?.getInt("id")

    println("todoId: $todoId")
    LaunchedEffect(todoId) {
        todoId?.let {
            viewModel.userAction(TodoDetailsAction.LoadData(it))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState) {
                is TodoDetailsUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is TodoDetailsUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("An error occurred.")
                        Button(onClick = {
                            todoId?.let { viewModel.userAction(TodoDetailsAction.Retry(it)) }
                        }) {
                            Text("Retry")
                        }
                    }
                }
                is TodoDetailsUiState.Success -> {
                    val todo = (uiState as TodoDetailsUiState.Success).data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Title: ${todo.title}", style = MaterialTheme.typography.headlineSmall)
                        Text("User ID: ${todo.userId}", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            "Completed: ${if (todo.completed) "Yes" else "No"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(text = todo.toString())
                    }
                }
            }
        }
    }
}