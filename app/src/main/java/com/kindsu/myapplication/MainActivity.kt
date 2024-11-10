package com.kindsu.myapplication

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kindsu.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lstPastillas = Pastillas()
    private lateinit var lstHoras: MutableMap<String, String>
    private lateinit var launchIntroducirPasti: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        launchIntroducirPasti = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleIntroducirPastiResult // Usa un método de referencia
        )

        lstHoras = mutableMapOf(
            "14/11/2024" to "13:50",
            "10/11/2024" to "14:50",
            "11/11/2024" to "16:50",
        )

        showCurrentDate()
        initListeners()
    }

    private fun handleIntroducirPastiResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            // Obtenemos los datos de la pastilla y la fecha desde el Intent de resultado
            val fecha = result.data?.getStringExtra("fecha")
            val nombrePasti = result.data?.getStringExtra("nombrePasti")

            if (fecha != null && nombrePasti != null) {
                // Añadimos la nueva pastilla a la lista usando la instancia de `lstPastillas`
                lstPastillas.addNuevaPastilla(fecha, nombrePasti)
            }

            // Llamamos a `showCurrentDate()` para actualizar la visualización
            showCurrentDate()
        }
    }

    private fun formatDate(calendar: Calendar): String {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        return "$day/$month/$year"
    }

    private fun showCurrentDate() {
        val calendar = Calendar.getInstance()
        val currentDate = formatDate(calendar)

        addCheckBox(currentDate)
        binding.tvFecha.text = currentDate
        binding.cvCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val formattedDate = formatDate(selectedDate)
            binding.tvFecha.text = formattedDate
            addCheckBox(formattedDate)
        }
    }

    private fun addCheckBox(date: String) {
        val txtColor = ContextCompat.getColor(this, R.color.txtColorOscuro)
        val fondoTxt = ResourcesCompat.getFont(this, R.font.font_sour_gummy)
        val pastis = lstPastillas.lstPastillas[date] ?: listOf("No tienes que tomar pastillas")

        binding.layoutCheckBox.removeAllViews()

        for (pasti in pastis) {
            val checkbox = CheckBox(this).apply {
                text = pasti
                textSize = 16f
                setTextColor(txtColor)
                setTypeface(fondoTxt, Typeface.BOLD)
                setOnCheckedChangeListener { _, isChecked ->
                    paintFlags = if (isChecked) {
                        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }
                }
            }
            binding.layoutCheckBox.addView(checkbox)
        }
    }

    private fun navigateToIntroducirPasti() {
        val intent = Intent(this, IntroducirPasti::class.java)
        launchIntroducirPasti.launch(intent)
    }

    private fun initListeners() {
        binding.btnAniadirPastilla.setOnClickListener {
            navigateToIntroducirPasti()
        }
    }
}