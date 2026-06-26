package com.example.playaroundbasics.data

import com.example.playaroundbasics.data.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface PostsService {
    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("/posts/{id}")
    suspend fun getPost(@Path("id") id: Long): Response<Post>
}