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
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val postsRepository: PostsRepository
) : ViewModel() {
    private val _posts = mutableStateOf<ApiResult<List<Post>>>(ApiResult.Progress)
    val posts: State<ApiResult<List<Post>>> = _posts

    init {
        loadPosts()
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
