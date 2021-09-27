package com.mr.bootcampweek4.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.mr.bootcampweek4.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // login control. default :false
        val isLoggedIn = intent.getBooleanExtra("isLogged", false)

        // if already logged go To-do fragment
        if (isLoggedIn) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.navigation)
            graph.startDestination = R.id.TodoFragment
            navHostFragment.navController.graph = graph
        }
    }
}
/*
        val navControlelr = findNavController(R.id.nav_host_fragment_container)
        NavigationUI.setupWithNavController(bottomnavigationview, navControlelr)
*/
