package com.kindsu.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kindsu.myapplication.databinding.ActivityIntroducirPastiBinding

class IntroducirPasti : AppCompatActivity() {
    private lateinit var binding: ActivityIntroducirPastiBinding
    private var lstPastillas = Pastillas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIntroducirPastiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListeners()
    }

    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { onTimeSelected(it)}
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelected(time: String) {
        binding.etHora.setText("$time")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.etFecha.setText("$day/${month + 1}/$year")
    }

    private fun getNombrePasti():String {
        return binding.etNombre.text.toString()
    }

    private fun initListeners(){
        binding.btnCancelar.setOnClickListener() {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnAceptar.setOnClickListener(){
            val intent = Intent()
            intent.putExtra("fecha", binding.etFecha.text.toString())
            intent.putExtra("nombrePasti", binding.etNombre.text.toString())
            setResult(RESULT_OK, intent)
            finish() // Termina la actividad y regresa a MainActivity
        }
        binding.etFecha.setOnClickListener(){
            showDatePickerDialog()
        }
        binding.etHora.setOnClickListener(){
            showTimePickerDialog()
        }
    }
}