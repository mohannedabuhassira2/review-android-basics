package com.example.playaroundbasics.domain

import com.example.playaroundbasics.data.ApiResult
import com.example.playaroundbasics.data.PostsService
import com.example.playaroundbasics.data.model.Post
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

internal class PostsRepository(
    private val postsService: PostsService = buildPostsService()
) {
    suspend fun getPosts(): ApiResult<List<Post>> {
        return safeApiCall { postsService.getPosts() }
    }

    suspend fun getPost(id: Long): ApiResult<Post> {
        return safeApiCall { postsService.getPost(id) }
    }

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): ApiResult<T> {
        return try {
            val response = apiCall()
            if (response.body() == null || !response.isSuccessful) {
                ApiResult.Failed("Response body is null")
            }
            ApiResult.Success(response.body()!!)
        } catch (e: HttpException) {
            ApiResult.Failed(e.message)
        } catch (e: IOException) {
            ApiResult.Failed(e.message)
        }
    }

    private companion object {
        fun buildPostsService(): PostsService {
            return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostsService::class.java)
        }
    }
}
