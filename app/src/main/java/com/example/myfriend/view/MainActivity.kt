package com.example.myfriend.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.view.setting.LocaleWrapper.wrap
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity()  {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var currentNavController: LiveData<NavController>? = null

    private val myRepository: MyRepository by inject()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(wrap(newBase))
    }

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

    override fun onDestroy() {
        myRepository.clear()
        super.onDestroy()
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

        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }


}