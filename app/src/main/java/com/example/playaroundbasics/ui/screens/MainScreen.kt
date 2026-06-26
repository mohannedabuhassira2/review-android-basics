package com.example.playaroundbasics.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun MainScreen(
    viewModel: MainViewModel
) {
    /*
     * - Flow: Asynchronous stream that emits multiple values sequentially.
     * - Cold Stream: The producer block ONLY runs when a terminal operator (like .collect) is called.
     * - Producer: Uses flow { ... } and emit(value) to send data.
     * - Consumer: Terminal operators (like .collect) trigger the flow and receive values.
     * - Compose World: Use .collectAsState() to bridge Flow to Compose.
     * - collect vs collectLatest:
     *   - collect: Processes every value; if processing takes time, it delays subsequent emissions.
     *   - collectLatest: If a new value arrives while the previous one is still being processed,
     *     the previous processing is cancelled and restarted with the new value.
     * - Changes in this commit highlight how we can use a typical cold flow in compose + viewmodel
     */
    val countDown by viewModel.countDown.collectAsState(10)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = countDown.toString())
        }
    }
}