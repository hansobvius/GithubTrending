package com.thiago.mobileui.browse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.thiago.mobileui.R
import com.thiago.mobileui.injection.ViewModelFactory
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        recycler_projects.layoutManager = LinearLayoutManager(this)
    }
}
