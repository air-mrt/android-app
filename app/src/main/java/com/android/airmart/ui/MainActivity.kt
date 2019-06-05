package com.android.airmart.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.android.airmart.R
import com.android.airmart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        setupActionBarWithNavController(this, navController)
        NavigationUI.setupWithNavController(navView, navController)
    }
    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.nav_fragment).navigateUp()
}
