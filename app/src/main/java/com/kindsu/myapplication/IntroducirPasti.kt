package com.kindsu.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kindsu.myapplication.databinding.ActivityIntroducirPastiBinding
import com.kindsu.myapplication.databinding.ActivityMainBinding

class IntroducirPasti : AppCompatActivity() {
    private lateinit var binding: ActivityIntroducirPastiBinding
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

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.etFecha.setText("$day/$month/$year")
    }

    private fun initListeners(){
        binding.btnCancelar.setOnClickListener(){
            onBackPressedDispatcher.onBackPressed()
        }
        binding.etFecha.setOnClickListener(){
            showDatePickerDialog()
        }
    }

}