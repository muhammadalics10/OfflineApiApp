package com.example.fourthtask.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fourthtask.R
import com.example.fourthtask.data.db.PostEntity

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val list = mutableListOf<PostEntity>()

    fun submitData(data: List<PostEntity>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.title.text = "${position + 1}. ${list[position].title}"
    }
}

