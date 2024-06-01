package com.example.memoriapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen)
        // Recuperamos el nombre de usuario de las preferencias
        val preferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val name = preferences.getString("name", null) // null será el valor por defecto
        Handler().postDelayed({
            // Si el usuario nunca ha iniciado la app, el valor es 'null' por defecto
            // Se inicia la pantalla de información del usuario
            if (name == null) {
                val userActivity = Intent(this@LauncherActivity, UserInfoActivity::class.java)
                startActivity(userActivity)
            } else {
                // Si el usuario SÍ se ha logueado, ya disponemos de su nombre de usuario
                // Se inicia la pantalla principal
                val mainActivity = Intent(this@LauncherActivity, MainActivity::class.java)
                startActivity(mainActivity)
            }
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_TIME_OUT = 2000 // Tiempo de duración de la pantalla de carga
    }
}