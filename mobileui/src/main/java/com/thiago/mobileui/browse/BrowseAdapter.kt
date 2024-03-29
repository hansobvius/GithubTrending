package com.thiago.mobileui.browse

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
import kotlinx.android.synthetic.main.item_view.view.*
import javax.inject.Inject

class BrowseAdapter @Inject constructor(): RecyclerView.Adapter<BrowseAdapter.ViewHolder>(){

    var projects: List<Project> = arrayListOf()
    var projectListener: ProjectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return projects.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]

        Glide.with(holder.itemView.context)
            .load(project.ownerAvatar)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.avatarImage)

        holder.ownerNameText.text = project.name
        holder.projectNameText.text = project.fullName

        val starResource = if(project.isBookmarked){
            R.drawable.ic_star_black_24dp
        }else{
            R.drawable.ic_star_border_black_24dp
        }

        holder.bookmarkedImage.setImageResource(starResource)

        holder.itemView.setOnClickListener{
            if(project.isBookmarked){
                projectListener?.onBookmarkedProjectClicked(project.id)
            }else{
                projectListener?.onProjectClicked(project.id)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var avatarImage: ImageView = view.image_owner_avatar
        var ownerNameText: TextView = view.text_owner_name
        var projectNameText: TextView = view.text_project_name
        var bookmarkedImage: ImageView = view.image_bookmarked

    }
}