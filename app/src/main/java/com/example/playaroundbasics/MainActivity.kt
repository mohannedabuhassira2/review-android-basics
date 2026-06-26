package com.example.playaroundbasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.playaroundbasics.ui.screens.MainScreen
import com.example.playaroundbasics.ui.screens.MainViewModel
import com.example.playaroundbasics.ui.theme.PlayAroundBasicsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

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