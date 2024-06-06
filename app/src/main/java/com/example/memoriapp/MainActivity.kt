package com.example.memoriapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.memoriapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
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
