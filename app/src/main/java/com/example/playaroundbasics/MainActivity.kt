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
        join: Suspend and wait until the job is done
        cancel: Cancel the job
        A Job transitions through: New -> Active -> Completing -> Completed (or Cancelled).
        Calling job.cancel() does NOT instantly kill the coroutine.
        It simply sets the job's "isCancelled" flag to true.
        Cancellation is COOPERATIVE: The coroutine must "check" this flag to stop.
        The coroutine needs a breathing room to "check" this flag to stop, which happens during suspension
        So, if the job is running a heavy non suspendable work (e.g. fib(50)), the check will take a long time.
         */

        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5) {
                Log.d(TAG, "Coroutine is still working...")
                delay(1000L.milliseconds)
            }
        }

        runBlocking {
            delay(3000L.milliseconds)
            job.cancel()
            Log.d(TAG, "Main Thread is continuing...")
        }
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