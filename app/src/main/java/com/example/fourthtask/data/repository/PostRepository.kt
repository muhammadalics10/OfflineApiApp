package com.example.fourthtask.data.repository

import com.example.fourthtask.data.api.ApiService
import com.example.fourthtask.data.db.PostDao
import com.example.fourthtask.data.db.PostEntity
import com.example.fourthtask.utils.AppLogger

class PostRepository(
    private val api: ApiService,
    private val dao: PostDao
) {
    val TAG = "PostRepository"

    suspend fun getPosts(isOnline: Boolean): List<PostEntity> {

        AppLogger.i(TAG, "Internet Available = $isOnline")

        if (isOnline) {
            try {
                AppLogger.i(TAG, "Fetching data from API")

                val apiPosts = api.getPosts()

                val entities = apiPosts.map {
                    PostEntity(it.id, it.title, it.body)
                }

                dao.insertPosts(entities)

                AppLogger.i(TAG, "API data saved to database (${entities.size})")

            } catch (e: Exception) {
                AppLogger.e(TAG, "API error: ${e.message}")
            }
        }

        val cached = dao.getPosts()
        AppLogger.i(TAG, "Loaded ${cached.size} records from database")

        return cached
    }
}

