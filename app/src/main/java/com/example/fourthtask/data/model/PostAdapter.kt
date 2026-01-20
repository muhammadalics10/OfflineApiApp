package com.example.fourthtask.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fourthtask.R
import com.example.fourthtask.data.db.PostEntity
import com.example.fourthtask.utils.AppLogger

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    val TAG = "PostAdapter"
    private val list = mutableListOf<PostEntity>()

    fun submitData(data: List<PostEntity>) {
        AppLogger.i(TAG, "Submitting ${data.size} items")
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.txtId)
        val title: TextView = view.findViewById(R.id.txtTitle)
        val body: TextView = view.findViewById(R.id.txtBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        AppLogger.d(TAG, "Creating ViewHolder")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]

        holder.id.text = "ID: ${post.id}"
        holder.title.text = post.title
        holder.body.text = post.body
    }
}


