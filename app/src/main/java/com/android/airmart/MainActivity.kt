package com.android.airmart

import android.content.res.Configuration
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.android.airmart.fragments.DisplayProductPostsFragment
import com.android.airmart.fragments.PostProductFragment

class MainActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                var displayProductPostsFragment = DisplayProductPostsFragment()
                if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.portrait_frame,  displayProductPostsFragment)
                        .commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_post -> {
                var postProductFragment = PostProductFragment()
                if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.portrait_frame,  postProductFragment)
                        .commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        var displayProductPostsFragment = DisplayProductPostsFragment()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.portrait_frame,  displayProductPostsFragment)
                .commit()
        }

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
