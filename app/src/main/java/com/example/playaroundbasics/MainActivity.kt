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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    val TAG = "MainActivityLogs"

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
        async block launches a coroutine that returns a deferred object as the result of the last operation.
        Allow us to run parallel suspend functions inside the coroutine
        async is on the same level as launch, the difference:
        1. launch returns a job like we saw before -> we can join it or cancel it
        2. async returns a deferred object of some generic type as a result
        To get the result of this deferred object, call wait on it, which will wait until it is ready
        */

        GlobalScope.launch(Dispatchers.IO) {
            val response1 = async(Dispatchers.IO) { doNetworkCall() }
            Log.d(TAG, "response1: ${response1.await()}")
            val response2 = async(Dispatchers.IO) { doNetworkCall() }
            Log.d(TAG, "response1: ${response2.await()}")
        }
    }
}

private suspend fun doNetworkCall(): String {
    delay(1000.milliseconds)
    return "Response"
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