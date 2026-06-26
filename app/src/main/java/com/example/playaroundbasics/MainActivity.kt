package com.example.playaroundbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.playaroundbasics.ui.screens.MainScreen
import com.example.playaroundbasics.ui.screens.MainViewModel
import com.example.playaroundbasics.ui.theme.PlayAroundBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<MainViewModel>{ MainViewModel.Factory }

        setContent {
            PlayAroundBasicsTheme {
                MainScreen(viewModel)
            }
        }
    }
}