package com.example.memoriapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Obtener referencia al SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        // Actualizar los TextView de contenido con los valores de SharedPreferences
        view.findViewById<TextView>(R.id.textViewContentName)?.text = sharedPreferences?.getString("name", "")
        view.findViewById<TextView>(R.id.textViewContentLastName)?.text = sharedPreferences?.getString("lastName", "")
        view.findViewById<TextView>(R.id.textViewContentDOB)?.text = sharedPreferences?.getString("dob", "")
        view.findViewById<TextView>(R.id.textViewContentChildren)?.text = sharedPreferences?.getString("childrenCount", "")
        view.findViewById<TextView>(R.id.textViewContentPet)?.text = sharedPreferences?.getString("petCount", "")

        return view
    }
}



