package com.kindsu.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kindsu.myapplication.databinding.ActivityIntroducirPastiBinding

class IntroducirPasti : AppCompatActivity() {
    private lateinit var binding: ActivityIntroducirPastiBinding
    private lateinit var lstPastillas: MutableMap<String, MutableList<String>>



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
            addNuevaPastilla()

            onBackPressedDispatcher.onBackPressed()
        }
        binding.etFecha.setOnClickListener(){
            showDatePickerDialog()
        }
        binding.etHora.setOnClickListener(){
            showTimePickerDialog()
        }
    }
    private fun addNuevaPastilla(){
        val passedMap = intent.getSerializableExtra("lstPastillas") as HashMap<String, MutableList<String>>
        if(passedMap != null){
            lstPastillas = passedMap

            if(lstPastillas.containsKey(binding.etFecha.text.toString())){
                lstPastillas[binding.etFecha.text.toString()]?.add(binding.etNombre.text.toString() ?: "")
            }else {
                lstPastillas[binding.etFecha.text.toString() ?: ""] = mutableListOf(binding.etFecha.text.toString() ?: "")
            }
        }
    }
}