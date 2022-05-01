package com.shoppi.app.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shoppi.app.R
import com.shoppi.app.ui.category.CategoryFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*---------------------------------------------*/

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_main)
        bottomNavigationView.itemIconTintList = null


        val navController = supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()
        navController?.let {
            bottomNavigationView.setupWithNavController(it)
        }

    }


    override fun onRestart() {
        super.onRestart()


        Handler(Looper.getMainLooper()).postDelayed({
            CategoryFragment().refreshAdapter()
        }, 1000)
    }


    /*---------------------------------------------*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()?.popBackStack()
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*---------------------------------------------*/


}