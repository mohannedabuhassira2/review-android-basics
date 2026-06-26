package com.example.playaroundbasics.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playaroundbasics.data.ApiResult
import com.example.playaroundbasics.data.model.Post
import com.example.playaroundbasics.domain.PostsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

internal class MainViewModel(
    private val postsRepository: PostsRepository
) : ViewModel() {
    private val _posts = mutableStateOf<ApiResult<List<Post>>>(ApiResult.Progress)
    val posts: State<ApiResult<List<Post>>> = _posts

    val countDown = flow {
        for (i in 10 downTo 0) {
            emit(i)
            delay(1000.milliseconds)
        }
    }

    init {
        collectFlow()
        loadPosts()
    }

    private fun collectFlow() {
        viewModelScope.launch {
            countDown.collect {
                println("collectFlow: $it")
            }
        }
    }

    fun loadPosts() {
        viewModelScope.launch {
            _posts.value = ApiResult.Progress
            val result = postsRepository.getPosts()
            _posts.value = result
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel(PostsRepository())
            }
        }
    }
}
