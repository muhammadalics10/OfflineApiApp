package com.example.fourthtask.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fourthtask.data.db.PostEntity
import com.example.fourthtask.data.repository.PostRepository
import com.example.fourthtask.utils.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: PostRepository
) : ViewModel() {
    val TAG = "MainViewModel"
    val postsLiveData = MutableLiveData<List<PostEntity>>()

    fun loadPosts(isOnline: Boolean) {

        AppLogger.d(TAG, "loadPosts called")

        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getPosts(isOnline)
            postsLiveData.postValue(data)
        }
    }
}

