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

        // 1. Launch Test (Outer try-catch FAIL)
        try {
            lifecycleScope.launch {
                Log.d("CoroutineTest", "1. Launch starting...")
                throw Exception("Launch Failed")
            }
        } catch (e: Exception) {
            // This will NEVER print because launch returns immediately
            Log.d("CoroutineTest", "1. Caught Launch: ${e.message}")
        }

        // 2. Async Test (Works with try-catch on .await())
        val deferred = lifecycleScope.async {
            Log.d("CoroutineTest", "2. Async starting...")
            throw Exception("Async Failed")
        }

        lifecycleScope.launch {
            try {
                deferred.await()
            } catch (e: Exception) {
                // This WILL catch the exception
                Log.d("CoroutineTest", "2. Caught Async: ${e.message}")
            }
        }

        // 3. runBlocking Test (Works because it's synchronous)
        try {
            runBlocking {
                Log.d("CoroutineTest", "3. runBlocking starting...")
                throw Exception("runBlocking Failed")
            }
        } catch (e: Exception) {
            // This WILL catch the exception
            Log.d("CoroutineTest", "3. Caught runBlocking: ${e.message}")
        }

        // 4. CoroutineScope will cancel all children if one fails
        // supervisorJob will prevent that.
        // We can also add + SupervisorJob() instead of the wrapper inside
        CoroutineScope(Dispatchers.IO).launch {
            //supervisorJob {
                launch {
                    delay(1000.milliseconds)
                    throw Exception("CoroutineScope Failed")
                }
                launch {
                    delay(2000.milliseconds)
                    throw Exception("CoroutineScope Failed")
                }
            //}
        }

        // 5. Swallowed Cancellation Test (The Bug)
        lifecycleScope.launch {
            val job = launch {
                try {
                    Log.d("CoroutineTest", "5. Swallowed Test: Starting...")
                    delay(500L.milliseconds)
                } catch (e: Exception) {
                    // This catches CancellationException!
                    Log.d("CoroutineTest", "5. Swallowed Test: Caught ${e.javaClass.simpleName}")
                }
                Log.d("CoroutineTest", "5. Swallowed Test: I finished anyway (BUG!)")
            }
            delay(300L.milliseconds)
            Log.d("CoroutineTest", "5. Swallowed Test: Cancelling...")
            job.cancel()
        }

        // 6. Proper Cancellation Test (The Fix)
        lifecycleScope.launch {
            val job = launch {
                try {
                    Log.d("CoroutineTest", "6. Proper Test: Starting...")
                    delay(500L.milliseconds)
                } catch (e: Exception) {
                    Log.d("CoroutineTest", "6. Proper Test: Caught ${e.javaClass.simpleName}")
                    if (e is CancellationException) {
                        throw e
                    }
                }
                Log.d("CoroutineTest", "6. Proper Test: I will never print this")
            }
            delay(300L)
            Log.d("CoroutineTest", "6. Proper Test: Cancelling...")
            job.cancel()
        }

        val viewModel by viewModels<MainViewModel>{ MainViewModel.Factory }

        setContent {
            PlayAroundBasicsTheme {
                MainScreen(viewModel)
            }
        }
    }
}