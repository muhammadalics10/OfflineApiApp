package com.example.fourthtask.data.repository

import com.example.fourthtask.data.api.ApiService
import com.example.fourthtask.data.db.PostDao
import com.example.fourthtask.data.db.PostEntity
import com.example.fourthtask.data.model.Post

class PostRepository(
    private val api: ApiService,
    private val dao: PostDao
) {

    suspend fun getPosts(isOnline: Boolean): List<PostEntity> {

        val count = dao.getCount()

        if (isOnline && count == 0) {
            // Fetch only once
            val apiPosts = api.getPosts()

            val entities = apiPosts.map {
                PostEntity(
                    id = it.id,
                    title = it.title,
                    body = it.body
                )
            }

            dao.insertPosts(entities)
        }

        // Always return cached data
        return dao.getPosts()
    }
}
