package com.example.fourthtask

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fourthtask.data.api.RetrofitClient
import com.example.fourthtask.data.db.AppDatabase
import com.example.fourthtask.data.repository.PostRepository
import com.example.fourthtask.ui.main.MainViewModel
import com.example.fourthtask.utils.NetworkUtils

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.getInstance(this)
        val repo = PostRepository(RetrofitClient.api, db.postDao())
        viewModel = MainViewModel(repo)

        val isOnline = NetworkUtils.isInternetAvailable(this)

        viewModel.loadPosts(isOnline)

        viewModel.postsLiveData.observe(this) {
            findViewById<TextView>(R.id.txtData).text = it.joinToString("\n") { post ->
                post.title
            }
        }

        if (!isOnline) {
            findViewById<ImageView>(R.id.imgNoInternet).visibility = View.VISIBLE
            findViewById<TextView>(R.id.txtMessage).visibility = View.VISIBLE
        }

        findViewById<Button>(R.id.btnCrash).setOnClickListener {
            throw RuntimeException("Test Crash")
        }
    }
}
