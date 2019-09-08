package com.thiago.mobileui.browse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.stetho.Stetho
import com.thiago.mobileui.R
import com.thiago.mobileui.injection.ViewModelFactory
import com.thiago.mobileui.mapper.ProjectViewMapper
import com.thiago.mobileui.model.Project
import com.thiago.presentation.BrowseProjectsViewModel
import com.thiago.presentation.model.ProjectView
import com.thiago.presentation.state.Resource
import com.thiago.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    @Inject lateinit var browseAdapter: BrowseAdapter
    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var browseViewModel: BrowseProjectsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        Stetho.initializeWithDefaults(this)
        AndroidInjection.inject(this)

        browseViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseProjectsViewModel::class.java)

        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.getProjects().observe(this,
            Observer<Resource<List<ProjectView>>>{
                it?.let{
                    handleDataState(it)
                }
            }
        )
        browseViewModel.fetchProjects()
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        when(resource.status){
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map{
                    mapper.mapFromView(it)
                }!!)
            }
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.GONE
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>){
        progress.visibility = View.GONE
        projects?.let{
            browseAdapter.projects = it
            browseAdapter.notifyDataSetChanged()
            recycler_projects.visibility = View.VISIBLE
        } ?: run{}
    }

    private val projectListener = object : ProjectListener{
        override fun onBookmarkedProjectClicked(projectId: String) {
            browseViewModel.unbookmarkedProject(projectId)
        }

        override fun onProjectClicked(projectId: String) {
            browseViewModel.bookmarkedProject(projectId)
        }
    }

    private fun setupRecyclerView(){
        browseAdapter.projectListener = projectListener
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = browseAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.image_bookmarked -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
