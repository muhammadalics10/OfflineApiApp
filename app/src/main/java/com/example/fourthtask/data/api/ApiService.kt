package com.example.fourthtask.data.api

import com.example.fourthtask.data.model.Post
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>
}
