package com.example.moderntodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.moderntodo.ui.screens.home.HomeScreen
import com.example.moderntodo.ui.screens.home.HomeViewModel
import com.example.moderntodo.ui.theme.ModernTodoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels {
        val app = application as TodoApplication
        HomeViewModel.Factory(app.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ModernTodoTheme {
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}
