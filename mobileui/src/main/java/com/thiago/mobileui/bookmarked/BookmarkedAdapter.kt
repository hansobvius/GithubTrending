package com.thiago.mobileui.bookmarked

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thiago.mobileui.R
import com.thiago.mobileui.model.Project
import kotlinx.android.synthetic.main.item_bookmarked_project.view.*
import javax.inject.Inject

class BookmarkedAdapter @Inject constructor(): RecyclerView.Adapter<BookmarkedAdapter.ViewHolder>(){

    var projects: List<Project> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmarked_project, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return projects.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]

        Glide.with(holder.itemView.context)
            .load(project.ownerAvatar)
            .apply(RequestOptions.centerCropTransform())
            .into(holder.avatarImage)

        holder.ownerTextView.text = project.name
        holder.projectTextView.text = project.fullName
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var avatarImage: ImageView = view.image_owner_avatar
        var ownerTextView: TextView = view.text_owner_name
        var projectTextView: TextView = view.text_project_name
    }
}