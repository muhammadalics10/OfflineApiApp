package com.example.fourthtask.data.repository

import com.example.fourthtask.data.api.ApiService
import com.example.fourthtask.data.db.PostDao
import com.example.fourthtask.data.db.PostEntity
import com.example.fourthtask.data.model.Post

class PostRepository(
    private val api: ApiService,
    private val dao: PostDao
) {

    suspend fun fetchPostsOnline(): List<Post> {
        return api.getPosts()
    }

    suspend fun savePostsOffline(posts: List<Post>) {
        val entities = posts.map {
            PostEntity(it.id, it.title, it.body)
        }
        dao.insertPosts(entities)
    }

    suspend fun getOfflinePosts(): List<PostEntity> {
        return dao.getPosts()
    }
}
