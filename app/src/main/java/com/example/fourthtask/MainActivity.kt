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
import com.example.fourthtask.utils.AppLogger
import com.example.fourthtask.utils.NetworkUtils
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    private lateinit var viewModel: MainViewModel
    private val adapter = PostAdapter()
    lateinit var noInternetLayout: View
    lateinit var recycler: RecyclerView
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppLogger.i(TAG, "App Started")

        recycler = findViewById<RecyclerView>(R.id.recyclerView)
        noInternetLayout = findViewById<View>(R.id.noInternetLayout)
        val btnRetry = findViewById<Button>(R.id.btnRetry)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        db = AppDatabase.getInstance(this)
        val repository = PostRepository(RetrofitClient.api, db.postDao())
        viewModel = MainViewModel(repository)

        btnRetry.setOnClickListener {
            AppLogger.i(TAG, "Retry clicked")
            loadData()
        }

        loadData()

        viewModel.postsLiveData.observe(this) {
            adapter.submitData(it)
        }

        findViewById<Button>(R.id.btnCrash).setOnClickListener {
            AppLogger.e(TAG, "Manual crash triggered")
            throw RuntimeException("Crashlytics Test Crash")
        }
    }

    fun loadData() {
        val isOnline = NetworkUtils.isInternetAvailable(this)
        AppLogger.i(TAG, "Internet = $isOnline")

        lifecycleScope.launch {
            val hasData = db.postDao().getCount() > 0

            if (!isOnline && !hasData) {
                AppLogger.w(TAG, "No internet and no cache")
                noInternetLayout.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                return@launch
            }

            noInternetLayout.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            viewModel.loadPosts(isOnline)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}



