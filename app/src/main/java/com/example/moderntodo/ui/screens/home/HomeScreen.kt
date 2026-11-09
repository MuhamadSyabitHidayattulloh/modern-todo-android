package com.example.moderntodo.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.domain.model.TodoItem
import com.example.moderntodo.ui.components.CategoryFilterRow
import com.example.moderntodo.ui.components.EmptyState
import com.example.moderntodo.ui.components.PriorityBadge
import com.example.moderntodo.ui.components.SearchBarM3
import com.example.moderntodo.ui.components.TodoCard
import com.example.moderntodo.ui.screens.detail.AddEditTodoSheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    var selectedTodoForEdit by remember { mutableStateOf<TodoItem?>(null) }
    var showPriorityFilterMenu by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Modern Todo",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${uiState.completedCount} dari ${uiState.totalCount} tugas selesai",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    actions = {
                        // Toggle hide completed
                        IconButton(
                            onClick = {
                                viewModel.onEvent(HomeUiEvent.ToggleHideCompleted(!uiState.hideCompleted))
                            }
                        ) {
                            Icon(
                                imageVector = if (uiState.hideCompleted) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Sembunyikan yang selesai",
                                tint = if (uiState.hideCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                // Progress Bar
                if (uiState.totalCount > 0) {
                    LinearProgressIndicator(
                        progress = { uiState.progressPercentage },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                // Search Bar
                SearchBarM3(
                    query = uiState.searchQuery,
                    onQueryChange = { viewModel.onEvent(HomeUiEvent.SearchQueryChanged(it)) }
                )

                // Category Filter Row
                CategoryFilterRow(
                    selectedCategory = uiState.selectedCategory,
                    onCategorySelected = { viewModel.onEvent(HomeUiEvent.CategorySelected(it)) }
                )

                // Priority Filter Chips Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Prioritas:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // All priorities
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(
                                if (uiState.selectedPriority == null) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                            .clickable {
                                viewModel.onEvent(HomeUiEvent.PrioritySelected(null))
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Semua",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (uiState.selectedPriority == null) FontWeight.Bold else FontWeight.Normal,
                            color = if (uiState.selectedPriority == null) MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Priority.entries.forEach { priority ->
                        val isSelected = uiState.selectedPriority == priority
                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.extraSmall)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                                .clickable {
                                    viewModel.onEvent(
                                        HomeUiEvent.PrioritySelected(if (isSelected) null else priority)
                                    )
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = priority.displayName,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    selectedTodoForEdit = null
                    showSheet = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.large,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Tugas"
                    )
                },
                text = {
                    Text(
                        text = "Tugas Baru",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.todos.isEmpty() -> {
                    EmptyState(
                        isSearching = uiState.searchQuery.isNotEmpty() || uiState.selectedCategory.name != "ALL",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(
                            items = uiState.todos,
                            key = { it.id }
                        ) { todo ->
                            TodoCard(
                                todo = todo,
                                onToggleCompleted = { completed ->
                                    viewModel.onEvent(HomeUiEvent.ToggleTodoCompleted(todo, completed))
                                },
                                onClick = {
                                    selectedTodoForEdit = todo
                                    showSheet = true
                                },
                                onDelete = {
                                    viewModel.onEvent(HomeUiEvent.DeleteTodo(todo))
                                },
                                modifier = Modifier.animateItemPlacement()
                            )
                        }
                    }
                }
            }
        }
    }

    if (showSheet) {
        AddEditTodoSheet(
            todoToEdit = selectedTodoForEdit,
            onDismiss = {
                showSheet = false
                selectedTodoForEdit = null
            },
            onSave = { todo ->
                viewModel.onEvent(HomeUiEvent.SaveTodo(todo))
            }
        )
    }
}
