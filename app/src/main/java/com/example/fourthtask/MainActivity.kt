package com.example.fourthtask

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fourthtask.data.api.RetrofitClient
import com.example.fourthtask.data.db.AppDatabase
import com.example.fourthtask.data.model.PostAdapter
import com.example.fourthtask.data.repository.PostRepository
import com.example.fourthtask.ui.main.MainViewModel
import com.example.fourthtask.utils.NetworkUtils
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val adapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val noInternetLayout = findViewById<View>(R.id.noInternetLayout)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val db = AppDatabase.getInstance(this)
        val repository = PostRepository(RetrofitClient.api, db.postDao())
        viewModel = MainViewModel(repository)

        val isOnline = NetworkUtils.isInternetAvailable(this)

        // FIRST LAUNCH NO INTERNET HANDLING
        lifecycleScope.launch {
            val hasData = db.postDao().getCount() > 0

            if (!isOnline && !hasData) {
                noInternetLayout.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                return@launch
            }

            noInternetLayout.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            viewModel.loadPosts(isOnline)
        }

        viewModel.postsLiveData.observe(this) {
            adapter.submitData(it)
        }

        // Crash Button
        findViewById<Button>(R.id.btnCrash).setOnClickListener {
            throw RuntimeException("Crash Test for Firebase")
        }
    }
}


