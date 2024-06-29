package com.example.proyectointegradorappsmoviles

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //var botonExportarVentas : Button = findViewById(R.layout.fragment_menu_opciones_imp_exp.)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView3) as NavHostFragment
        val navController = navHostFragment.navController
        //toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        setSupportActionBar(findViewById(R.id.toolbar))

        setupActionBarWithNavController(navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        //return super.onSupportNavigateUp()

        /*val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController*/

        val navController = findNavController(R.id.fragmentContainerView3)
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}