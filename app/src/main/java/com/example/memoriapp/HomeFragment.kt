package com.example.memoriapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment

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

        }

        microfonoButton.setOnClickListener {

        }

        libretaDeContactosButton.setOnClickListener {

        }

        camaraButton.setOnClickListener {

        }

        calendarioButton.setOnClickListener {

        }

        corazonButton.setOnClickListener {

        }

        return view
    }
}
