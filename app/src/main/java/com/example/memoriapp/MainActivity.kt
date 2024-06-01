package com.example.memoriapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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