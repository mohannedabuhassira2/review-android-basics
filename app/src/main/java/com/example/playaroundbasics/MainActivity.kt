package com.example.playaroundbasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.playaroundbasics.ui.theme.PlayAroundBasicsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayAroundBasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        /*
        runBlocking does this:
        1. Launches a new coroutine in main
        2. Keeps the coroutine glued when a suspend function is called, so that
           functionality is broken here and that forces the main thread to be blocked
        We use it mostly for unit testing to ensure we block the main thread
        Before we continue, understand that launch is fire and forget and not really a suspend function
        What will happen:
        1. runBlocking comes into action
        2. Launches a new coroutine in main that'll run concurently in IO
        3. Launches a second coroutine in main that'll run concurently in IO
        4. runBlocking will interact with the 1st supsend function delay and will block the main thread for 1s
        5. After 1s, "runBlocking block 'main' code finished at 1000ms" will be printed
        6. Both of the courotines will be finished that are launched in IO
        7. All childs are done and the whole runBlocking coroutine is done, so the last log will be printed

        Changing to a regular launch on top instead of runBlocking will have the last log to be printed immediately
         */

        GlobalScope.launch {
            Log.d(TAG, "Starting runBlocking...")

            launch(Dispatchers.IO) {
                delay(3000.milliseconds)
                Log.d(TAG, "Child 1 finished at 3000ms")
            }

            launch(Dispatchers.IO) {
                delay(3000.milliseconds)
                Log.d(TAG, "Child 2 finished at 3000ms")
            }

            delay(1000.milliseconds)
            Log.d(TAG, "runBlocking block 'main' code finished at 1000ms")
        }

        Log.d(TAG, "The whole thing is done")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlayAroundBasicsTheme {
        Greeting("Android")
    }
}