package com.example.memoriapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.memoriapp.HomeFragment
import com.example.memoriapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usuarioButton = findViewById<Button>(R.id.button)

        usuarioButton.setOnClickListener {
            // Crear una instancia del fragmento HomeFragment
            val homeFragment = HomeFragment()

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
}
