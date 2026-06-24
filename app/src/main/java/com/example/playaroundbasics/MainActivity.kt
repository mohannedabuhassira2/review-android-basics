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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        GlobalScope.launch {
            // Suspends the coroutine until the network call is complete
            val networkCall1 = doNetworkCall()
            // Continue the coroutine
            Log.d(TAG, "Network call 1 done: $networkCall1")
            // Suspends again the coroutine until the network call is complete
            val networkCall2 = doNetworkCall()
            // Continue the coroutine again
            Log.d(TAG, "Network call 2 done: $networkCall2")
            // Total time here is 2000ms
        }
    }

    private suspend fun doNetworkCall(): String {
        delay(1000L.milliseconds)
        return "This is a response"
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