package com.kindsu.myapplication

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kindsu.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lstPastillas: MutableMap<String, MutableList<String>>
    private lateinit var lstHoras: MutableMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lstPastillas = mutableMapOf(
            "14/11/2024" to mutableListOf("Tirodrill", "Furosemida", "Ferbisol"),
            "10/11/2024" to mutableListOf("Omeprazol", "Trajenta", "Apixaban"),
            "11/11/2024" to mutableListOf("Bisoprolol", "Loxartan", "Alodipino")
        )
        lstHoras = mutableMapOf(
            "14/11/2024" to "13:50",
            "10/11/2024" to "14:50",
            "11/11/2024" to "16:50",
        )

        showCurrentDate() // Mostrar la fecha actual y las pastillas al inicio
        initListeners()

    }

    //Recibo un parametro tipo calendario y lo convierto a string recogiendo sus valores dia, mes y año
    private fun formatDate(calendar: Calendar): String {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        return "$day/$month/$year"
    }

    private fun showCurrentDate() {                                                 // Mostrar la fecha actual en el TextView al iniciar la actividad
        val calendar = Calendar.getInstance()
        val currentDate = formatDate(calendar)

        addCheckBox(currentDate)                                                    // Mostrar la lista de pastillas para la fecha actual
        binding.tvFecha.text = currentDate                                          // Mostrar la fecha actual
        binding.cvCalendar.setOnDateChangeListener{ _, year, month, dayOfMonth ->   // Configurar el listener para cuando el usuario seleccione una fecha
            val selectedDate = Calendar.getInstance().apply {                       // Recogemos la fecha que indica el usuario
                set(year, month, dayOfMonth) }
            val formattedDate = formatDate(selectedDate)                            //Formatear la fecha que hemos recogido de Calendar a string
            binding.tvFecha.text = formattedDate                                    // Actualizar la fecha en el TextView
            addCheckBox(formattedDate)                                              // Mostrar las pastillas asociadas a la fecha seleccionada
        }
    }

    private fun addCheckBox(date: String) {
        val txtColor = ContextCompat.getColor(this, R.color.txtColorOscuro)         //Recogemos el color de la letra en una variable
        val fondoTxt = ResourcesCompat.getFont(this, R.font.font_sour_gummy)        //Recogemos el estilo de la letra en una variable
        val pastis = lstPastillas[date] ?: listOf("No tienes que tomar pastillas")          //la clave fecha no existe en el diccionario, mostraremos un checkbox con el texto

        binding.layoutCheckBox.removeAllViews()                                             // Limpiar cualquier CheckBox previo en el contenedor

        // Añadir los CheckBox al layout
        for (pasti in pastis) {
            val checkbox = CheckBox(this)
            checkbox.text = pasti
            checkbox.textSize = 16f
            checkbox.setTextColor(txtColor)
            checkbox.setTypeface(fondoTxt, Typeface.BOLD)
            checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkbox. paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                checkbox. paintFlags = checkbox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
            binding.layoutCheckBox.addView(checkbox)
        }
    }

    private fun navigateToIntroducirPasti(){
        val intent = Intent(this, IntroducirPasti::class.java)
        intent.putExtra("mapaPastis", HashMap(lstPastillas))
        startActivity(intent)
    }

    private fun initListeners(){
        binding.btnAniadirPastilla.setOnClickListener(){
            navigateToIntroducirPasti()
        }
    }
}