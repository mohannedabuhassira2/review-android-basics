package com.example.playaroundbasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
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
                    Box (
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button (
                            onClick = {
                                finish()
                            }
                        ) {
                            Text("Click me to destroy the activity")
                        }
                    }
                }
            }
        }

        GlobalScope.launch {
            while (true) {
                Log.d(TAG, "Doing something bad here cause if the activity is destroyed, we'll still use resources of the activity -> memory leak")
                delay(1000.milliseconds)
            }
        }

        lifecycleScope.launch {
            while (true) {
                Log.d(TAG, "Instead we scope the lifecycle to the scope of the activity")
                delay(1000.milliseconds)
            }
        }

        // viewModelScope work in a similar fashion for view models
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
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