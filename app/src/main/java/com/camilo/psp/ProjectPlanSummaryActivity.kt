package com.camilo.psp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProjectPlanSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        TODO -- VOY A AGREGAR EL BTN BACK EN TODAS LAS ACTIVIDADES
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val projectId = intent.extras!!.get("ProjectId").toString()

        val navTimeInPhaseBundle = Bundle()
        navTimeInPhaseBundle.putString("ProjectId", projectId)

        val navDefectsInjectedBundle = Bundle()
        navDefectsInjectedBundle.putString("ProjectId", projectId)

        val navDefectsRemovedBundle = Bundle()
        navDefectsRemovedBundle.putString("ProjectId", projectId)


        setContentView(R.layout.activity_project_plan_summary)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.navigation_time_in_phase, navTimeInPhaseBundle)
        navController.navigate(R.id.navigation_defects_injected, navDefectsInjectedBundle)
        navController.navigate(R.id.navigation_defects_removed, navDefectsRemovedBundle)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_time_in_phase,
                R.id.navigation_defects_injected,
                R.id.navigation_defects_removed
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
