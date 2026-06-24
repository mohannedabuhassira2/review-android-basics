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

        // GlobalScope is bad but let's just do that for now
        // Idea is that this coroutine still lives until the app is killed which
        // isn't the best with Android lifecycle
        GlobalScope.launch {
            // Sleep here pauses the coroutine but not the thread
            // So it'll suspend the coroutine until the work of this suspend function
            // is done and then resume the coroutine
            Log.d(TAG, "Hello from coroutine: ${Thread.currentThread().name}")
            delay(1000L)
            Log.d(TAG, "Hello from coroutine: ${Thread.currentThread().name}")
        }

        Log.d(TAG, "Hello from thread: ${Thread.currentThread().name}")
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