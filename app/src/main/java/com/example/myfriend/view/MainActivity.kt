package com.example.myfriend.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.view.nation.NationContract
import com.example.myfriend.view.nation.NationPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()  {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       setSupportActionBar(findViewById(R.id.toolbar))

        if(savedInstanceState == null){
            setUpBottomNavigationBar()
        }

/*
        val navController = findNavController(R.id.nav_host_container)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
*/



    }

    private fun setUpBottomNavigationBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav)
        val navGraphIds = listOf(
            R.navigation.navigation_home,
            R.navigation.navigation_nation,
            R.navigation.navigation_tag,
            R.navigation.navigation_setting
        )

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds, supportFragmentManager, R.id.nav_host_container, intent
        )

        controller.observe(this, Observer {navController->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }


}