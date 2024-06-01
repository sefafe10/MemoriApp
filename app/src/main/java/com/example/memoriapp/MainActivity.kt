package com.example.memoriapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.memoriapp.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.Toast
import com.example.memoriapp.HomeFragment
import com.example.memoriapp.ProfileFragment
import com.example.memoriapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_profile
            )
        )
        // Elimina esta línea para evitar la configuración de ActionBar
        // setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    fun sendUserDataToProfileFragment() {
        val sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val bundle = Bundle()
        bundle.putString("name", sharedPreferences.getString("name", ""))
        bundle.putString("lastName", sharedPreferences.getString("lastName", ""))
        bundle.putString("dob", sharedPreferences.getString("dob", ""))
        bundle.putBoolean("hasChildren", sharedPreferences.getBoolean("hasChildren", false))
        bundle.putString("childrenCount", sharedPreferences.getString("childrenCount", ""))
        bundle.putBoolean("hasPet", sharedPreferences.getBoolean("hasPet", false))
        bundle.putString("petCount", sharedPreferences.getString("petCount", ""))

        val profileFragment = ProfileFragment()
        profileFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, profileFragment)
            .commit()
    }
}
