package com.example.memoriapp

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    private val REQUEST_CALL_PERMISSION = 1
    private val SOS_PHONE_NUMBER = " "

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
        val sosButton = view.findViewById<ImageButton>(R.id.sos)

        // Crear un color negro
        val colorNegro = ContextCompat.getColor(requireContext(), android.R.color.black)

        // Aplicar el color negro a los drawables de los ImageButtons
        usuarioButton.setColorFilter(colorNegro, PorterDuff.Mode.SRC_IN)
        microfonoButton.setColorFilter(colorNegro, PorterDuff.Mode.SRC_IN)
        libretaDeContactosButton.setColorFilter(colorNegro, PorterDuff.Mode.SRC_IN)
        camaraButton.setColorFilter(colorNegro, PorterDuff.Mode.SRC_IN)
        calendarioButton.setColorFilter(colorNegro, PorterDuff.Mode.SRC_IN)

        // Configurar OnClickListener para cada botón
        usuarioButton.setOnClickListener {
            // Navegar a ProfileFragment
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
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

        sosButton.setOnClickListener {
            makePhoneCall()
        }

        return view
    }


    private fun makePhoneCall() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
        } else {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$SOS_PHONE_NUMBER")
            startActivity(callIntent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(requireContext(), "Permiso para realizar llamadas denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
