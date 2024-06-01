package com.example.memoriapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.memoriapp.ProfileFragment
import com.example.memoriapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usuarioButton = findViewById<Button>(R.id.button)

        usuarioButton.setOnClickListener {
            // Crear una instancia del fragmento HomeFragment
            val homeFragment = ProfileFragment()

            // Obtener el FragmentManager
            val fragmentManager = supportFragmentManager

            // Iniciar una transacción
            val transaction = fragmentManager.beginTransaction()

            // Reemplazar el contenido del contenedor principal con el fragmento HomeFragment
            transaction.replace(R.id.activity_main, homeFragment)

            // Agregar la transacción al BackStack
            transaction.addToBackStack(null)

            // Confirmar la transacción
            transaction.commit()
        }
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