package com.example.memoriapp


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserInfoActivity : AppCompatActivity() {

    private var editTextName: EditText? = null
    private var editTextLastName: EditText? = null
    private var editTextDOB: EditText? = null
    private var editTextNumber: EditText? = null
    private var editTextChildren: EditText? = null
    private var editTextPetName: EditText? = null
    private var checkBoxChildren: CheckBox? = null
    private var checkBoxPet: CheckBox? = null
    private var buttonSubmit: Button? = null
    private var buttonAddChildren: ImageButton? = null
    private var buttonAddPet: ImageButton? = null
    private var textViewChildrenCount: TextView? = null
    private var textViewPetCount: TextView? = null
    private var myCalendar: Calendar? = null
    private var childrenAdded = false
    private var petsAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        editTextName = findViewById(R.id.editTextName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextDOB = findViewById(R.id.editTextDOB)
        editTextNumber = findViewById(R.id.editTextNumber)
        editTextChildren = findViewById(R.id.editTextChildren)
        editTextPetName = findViewById(R.id.editTextPetName)
        checkBoxChildren = findViewById(R.id.checkBoxChildren)
        checkBoxPet = findViewById(R.id.checkBoxPet)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        buttonAddChildren = findViewById(R.id.buttonAddSon)
        buttonAddPet = findViewById(R.id.buttonAddPet)
        textViewChildrenCount = findViewById(R.id.textViewChildrenCount)
        textViewPetCount = findViewById(R.id.textViewPetCount)
        myCalendar = Calendar.getInstance()

        val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar?.set(Calendar.YEAR, year)
            myCalendar?.set(Calendar.MONTH, monthOfYear)
            myCalendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        editTextDOB?.setOnClickListener {
            DatePickerDialog(
                this@UserInfoActivity, date, myCalendar!!
                    .get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkSubmitButton()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        editTextName?.addTextChangedListener(textWatcher)
        editTextLastName?.addTextChangedListener(textWatcher)
        editTextDOB?.addTextChangedListener(textWatcher)
        editTextNumber?.addTextChangedListener(textWatcher)
        editTextChildren?.addTextChangedListener(textWatcher)
        editTextPetName?.addTextChangedListener(textWatcher)

        checkBoxChildren?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textViewChildrenCount?.visibility = View.VISIBLE
                editTextChildren?.visibility = View.VISIBLE
                editTextChildren?.requestFocus()
                buttonAddChildren?.visibility = View.VISIBLE
            } else {
                textViewChildrenCount?.visibility = View.GONE
                editTextChildren?.visibility = View.GONE
                editTextChildren?.text = null
                buttonAddChildren?.visibility = View.GONE
                clearDynamicEditTexts(R.id.layout_hijos)
                childrenAdded = false
                checkSubmitButton()
            }
        }

        checkBoxPet?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textViewPetCount?.visibility = View.VISIBLE
                editTextPetName?.visibility = View.VISIBLE
                editTextPetName?.requestFocus()
                buttonAddPet?.visibility = View.VISIBLE
            } else {
                textViewPetCount?.visibility = View.GONE
                editTextPetName?.visibility = View.GONE
                editTextPetName?.text = null
                buttonAddPet?.visibility = View.GONE
                clearDynamicEditTexts(R.id.layout_mascotas)
                petsAdded = false
                checkSubmitButton()
            }
        }

        buttonSubmit?.setOnClickListener {
            if (validateFields()) {
                if (checkBoxChildren?.isChecked == true && !childrenAdded) {
                    Toast.makeText(this, "Por favor, pulsa el \"+\"", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (checkBoxPet?.isChecked == true && !petsAdded) {
                    Toast.makeText(this, "Por favor, añade los nombres de las mascotas", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                submitForm()
                clearDynamicEditTexts(R.id.layout_hijos)
                clearDynamicEditTexts(R.id.layout_mascotas)
            }
        }

        buttonAddChildren?.setOnClickListener {
            val childrenCountString = editTextChildren?.text.toString().trim()
            if (childrenCountString.isNotEmpty()) {
                try {
                    val childrenCount = childrenCountString.toInt()
                    if (childrenCount in 1..15) {
                        addChildrenEditTexts(childrenCount)
                        childrenAdded = true
                        checkSubmitButton()
                    } else {
                        editTextChildren?.error = "Por favor introduce un número válido entre 1 y 15"
                    }
                } catch (e: NumberFormatException) {
                    editTextChildren?.error = "Por favor introduce un número válido"
                }
            } else {
                editTextChildren?.error = "Por favor introduce el número de hijos/as"
            }
        }

        buttonAddPet?.setOnClickListener {
            val petCountString = editTextPetName?.text.toString().trim()
            if (petCountString.isNotEmpty()) {
                try {
                    val petCount = petCountString.toInt()
                    if (petCount in 1..15) {
                        addPetEditTexts(petCount)
                        petsAdded = true
                        checkSubmitButton()
                    } else {
                        editTextPetName?.error = "Por favor introduce un número válido entre 1 y 15"
                    }
                } catch (e: NumberFormatException) {
                    editTextPetName?.error = "Por favor introduce un número válido"
                }
            } else {
                editTextPetName?.error = "Por favor introduce el número de mascotas"
            }
        }

        checkSubmitButton() // Inicializar el estado del botón de envío
    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy" // Formato deseado
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editTextDOB?.setText(sdf.format(myCalendar!!.time))
    }

    private fun addChildrenEditTexts(count: Int) {
        val layout = findViewById<LinearLayout>(R.id.layout_hijos)
        layout.removeAllViews()
        for (i in 0 until count) {
            val childEditText = EditText(this)
            childEditText.id = View.generateViewId()
            childEditText.hint = "Nombre del hijo/a " + (i + 1)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 10, 0, 10) // Añadir margen vertical de 10dp
            layout.addView(childEditText, params)
        }
    }

    private fun addPetEditTexts(count: Int) {
        val layout = findViewById<LinearLayout>(R.id.layout_mascotas)
        layout.removeAllViews()
        for (i in 0 until count) {
            val petEditText = EditText(this)
            petEditText.id = View.generateViewId()
            petEditText.hint = "Nombre de la mascota " + (i + 1)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 10, 0, 10) // Añadir margen vertical de 10dp
            layout.addView(petEditText, params)
        }
    }

    private fun clearDynamicEditTexts(layoutId: Int) {
        val layout = findViewById<LinearLayout>(layoutId)
        layout.removeAllViews()
    }

    private fun validateFields(): Boolean {
        var isValid = true

        val fields = listOf(
            editTextName,
            editTextLastName,
            editTextDOB,
            editTextNumber
        )

        for (field in fields) {
            if (field?.text.toString().trim().isEmpty()) {
                field?.error = "Este campo es obligatorio"
                isValid = false
            }
        }

        if (checkBoxChildren?.isChecked == true) {
            if (editTextChildren?.text.toString().trim().isEmpty()) {
                editTextChildren?.error = "Por favor introduce el número de hijos/as"
                isValid = false
            } else {
                val childrenLayout = findViewById<LinearLayout>(R.id.layout_hijos)
                for (i in 0 until childrenLayout.childCount) {
                    val childEditText = childrenLayout.getChildAt(i) as EditText
                    if (childEditText.text.toString().trim().isEmpty()) {
                        childEditText.error = "Este campo es obligatorio"
                        isValid = false
                    }
                }
            }
        }

        if (checkBoxPet?.isChecked == true) {
            if (editTextPetName?.text.toString().trim().isEmpty()) {
                editTextPetName?.error = "Por favor introduce el número de mascotas"
                isValid = false
            } else {
                val petsLayout = findViewById<LinearLayout>(R.id.layout_mascotas)
                for (i in 0 until petsLayout.childCount) {
                    val petEditText = petsLayout.getChildAt(i) as EditText
                    if (petEditText.text.toString().trim().isEmpty()) {
                        petEditText.error = "Este campo es obligatorio"
                        isValid = false
                    }
                }
            }
        }

        return isValid
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun redirectToProfileFragment() {
        (this as? MainActivity)?.sendUserDataToProfileFragment()
    }

    private fun submitForm() {
        val name = editTextName?.text.toString().trim()
        val lastName = editTextLastName?.text.toString().trim()
        val dob = editTextDOB?.text.toString().trim()
        val number = editTextNumber?.text.toString().trim()

        // Guardar en SharedPreferences
        val sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("lastName", lastName)
        editor.putString("dob", dob)
        editor.putString("number", number)

        // Guardar los nombres de los hijos/as si se han añadido
        if (checkBoxChildren?.isChecked == true && childrenAdded) {
            val childrenLayout = findViewById<LinearLayout>(R.id.layout_hijos)
            for (i in 0 until childrenLayout.childCount) {
                val childEditText = childrenLayout.getChildAt(i) as EditText
                val childName = childEditText.text.toString().trim()
                editor.putString("child_$i", childName)
            }
            editor.putInt("childrenCount", childrenLayout.childCount)
        } else {
            editor.putInt("childrenCount", 0)
        }

        // Guardar los nombres de las mascotas si se han añadido
        if (checkBoxPet?.isChecked == true && petsAdded) {
            val petsLayout = findViewById<LinearLayout>(R.id.layout_mascotas)
            for (i in 0 until petsLayout.childCount) {
                val petEditText = petsLayout.getChildAt(i) as EditText
                val petName = petEditText.text.toString().trim()
                editor.putString("pet_$i", petName)
            }
            editor.putInt("petCount", petsLayout.childCount)
        } else {
            editor.putInt("petCount", 0)
        }

        editor.apply()
        Toast.makeText(this, "Información guardada correctamente", Toast.LENGTH_SHORT).show()
        redirectToMainActivity()
        redirectToProfileFragment()
    }

    private fun checkSubmitButton() {
        buttonSubmit?.isEnabled = (editTextName?.text?.isNotEmpty() == true &&
                editTextLastName?.text?.isNotEmpty() == true &&
                editTextDOB?.text?.isNotEmpty() == true && editTextNumber?.text?.isNotEmpty() == true) ||
                childrenAdded || petsAdded
    }
}
