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

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textViewName: TextView
    private lateinit var textViewLastName: TextView
    private lateinit var textViewDOB: TextView
    private lateinit var textViewContentName: TextView
    private lateinit var textViewContentLastName: TextView
    private lateinit var textViewContentDOB: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Obtener referencia al SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        // Inicializar TextViews
        textViewName = view.findViewById(R.id.textViewName)
        textViewLastName = view.findViewById(R.id.textViewLastName)
        textViewDOB = view.findViewById(R.id.textViewDOB)
        textViewContentName = view.findViewById(R.id.textViewContentName)
        textViewContentLastName = view.findViewById(R.id.textViewContentLastName)
        textViewContentDOB = view.findViewById(R.id.textViewContentDOB)

        // Leer los valores almacenados en SharedPreferences y mostrarlos en los TextViews
        val name = sharedPreferences.getString("name", "")
        val lastName = sharedPreferences.getString("lastName", "")
        val dob = sharedPreferences.getString("dob", "")

        textViewContentName.text = name
        textViewContentLastName.text = lastName
        textViewContentDOB.text = dob
        // Obtener los LinearLayouts
        val childrenLayout = view.findViewById<LinearLayout>(R.id.childrenLayout)
        val petsLayout = view.findViewById<LinearLayout>(R.id.petsLayout)

        // Limpiar los LinearLayouts
        childrenLayout.removeAllViews()
        petsLayout.removeAllViews()

        // Obtener y mostrar los nombres de los hijos/as
        val childrenCount = sharedPreferences.getInt("childrenCount", 0)
        for (i in 0 until childrenCount) {
            val childName = sharedPreferences.getString("child_$i", "") ?: ""
            if (childName.isNotEmpty()) {
                addChildTextView(childName, childrenLayout)
            }
        }

        // Obtener y mostrar los nombres de las mascotas
        val petCount = sharedPreferences.getInt("petCount", 0)
        for (i in 0 until petCount) {
            val petName = sharedPreferences.getString("pet_$i", "") ?: ""
            if (petName.isNotEmpty()) {
                addPetTextView(petName, petsLayout)
            }
        }

        return view
    }

    private fun addChildTextView(childName: String, parentLayout: LinearLayout) {
        val textView = TextView(requireContext())
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.textSize = 20f
        textView.setPadding(0, 40, 0, 0)
        textView.text = childName
        parentLayout.addView(textView)
    }

    private fun addPetTextView(petName: String, parentLayout: LinearLayout) {
        val textView = TextView(requireContext())
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.textSize = 20f
        textView.setPadding(0, 40, 0, 0)
        textView.text = petName
        parentLayout.addView(textView)
    }
}









