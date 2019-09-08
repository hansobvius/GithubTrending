package com.thiago.mobileui.bookmarked

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.thiago.mobileui.R
import com.thiago.mobileui.injection.ViewModelFactory
import com.thiago.mobileui.mapper.ProjectViewMapper
import com.thiago.mobileui.model.Project
import com.thiago.presentation.BrowseBookmarkedProjectsViewModel
import com.thiago.presentation.model.ProjectView
import com.thiago.presentation.state.Resource
import com.thiago.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_bookmarked.*
import javax.inject.Inject

class BookmarkedActivity : AppCompatActivity() {

    @Inject lateinit var bookmarkedAdapter: BookmarkedAdapter
    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var browseProjectsViewModel: BrowseBookmarkedProjectsViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, BookmarkedActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked)
        AndroidInjection.inject(this)

        browseProjectsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseBookmarkedProjectsViewModel::class.java)

        setupBrowserRecycler()
    }

    override fun onStart(){
        super.onStart()
        browseProjectsViewModel.getProjects().observe(this,
            Observer<Resource<List<ProjectView>>>{
                it?.let{
                    handelStateData(it)
                }
            })
        browseProjectsViewModel.fetchProjects()
    }

    private fun setupBrowserRecycler(){
        recycler_projects_bookmarked.layoutManager = LinearLayoutManager(this)
        recycler_projects_bookmarked.adapter = bookmarkedAdapter
    }

    private fun handelStateData(resource: Resource<List<ProjectView>>){
        when(resource.status){
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map{
                    mapper.mapFromView(it)
                })
                progress_bookmarked.visibility = View.GONE
                recycler_projects_bookmarked.visibility = View.VISIBLE
            }
            ResourceState.LOADING -> {
                progress_bookmarked.visibility = View.VISIBLE
                recycler_projects_bookmarked.visibility = View.GONE
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?){
        projects?.let{
            bookmarkedAdapter.projects = it
            bookmarkedAdapter.notifyDataSetChanged()
        }?: run{

        }
    }
}
