package com.example.memoriapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.provider.MediaStore
import android.provider.ContactsContract


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Buscar los ImageButtons por sus IDs
        val usuarioButton = view.findViewById<ImageButton>(R.id.usuario)
        val microfonoButton = view.findViewById<ImageButton>(R.id.microfono)
        val libretaDeContactosButton = view.findViewById<ImageButton>(R.id.libreta_de_contactos)
        val camaraButton = view.findViewById<ImageButton>(R.id.camara)
        val calendarioButton = view.findViewById<ImageButton>(R.id.calendario)
        val corazonButton = view.findViewById<ImageButton>(R.id.corazon)

        // Configurar OnClickListener para cada botón
        usuarioButton.setOnClickListener {
            // Acción cuando se hace clic en el botón de usuario
            // Aquí puedes agregar la lógica correspondiente
        }

        microfonoButton.setOnClickListener {
            // Intent para abrir la aplicación de grabación de sonidos
            val packageManager = requireContext().packageManager
            val intent = packageManager.getLaunchIntentForPackage("com.android.soundrecorder")
            if (intent != null) {
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Aplicación de grabación de sonidos no encontrada", Toast.LENGTH_SHORT).show()
            }
        }

        libretaDeContactosButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Manejar la excepción si la actividad no está disponible
                Toast.makeText(context, "No se pudo abrir la aplicación de contactos", Toast.LENGTH_SHORT).show()
            }




        }


        camaraButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivity(intent)
        }




        calendarioButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                // Configurar el paquete de la aplicación de calendario (puede variar según el dispositivo)
                setClassName("com.google.android.calendar", "com.android.calendar.AllInOneActivity")
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Manejar la excepción si la actividad no está disponible
                Toast.makeText(context, "No se pudo encontrar la aplicación de calendario", Toast.LENGTH_SHORT).show()
            }
        }



        corazonButton.setOnClickListener {
            // Acción cuando se hace clic en el botón de corazón
            // Aquí puedes agregar la lógica correspondiente
        }

        return view
    }
}
