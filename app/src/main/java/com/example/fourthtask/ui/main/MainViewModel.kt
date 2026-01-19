package com.example.fourthtask.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fourthtask.data.db.PostEntity
import com.example.fourthtask.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository) : ViewModel() {

    val postsLiveData = MutableLiveData<List<PostEntity>>()

    fun loadPosts(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {

            if (isOnline) {
                val apiPosts = repository.fetchPostsOnline()
                repository.savePostsOffline(apiPosts)
            }

            val offlinePosts = repository.getOfflinePosts()
            postsLiveData.postValue(offlinePosts)
        }
    }
}
