package com.example.playaroundbasics.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.playaroundbasics.data.ApiResult

@Composable
internal fun MainScreen(
    viewModel: MainViewModel
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val postsResult = viewModel.posts.value

        Box(
            modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (postsResult) {
                is ApiResult.Progress -> {
                    CircularProgressIndicator()
                }

                is ApiResult.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(postsResult.data) { post ->
                            Text(text = post.toString())
                        }
                    }
                }

                is ApiResult.Failed -> {
                    Text(text = "Failed")
                }
            }
        }
    }
}