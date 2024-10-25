package com.eminokumus.a7minutesworkoutapp.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.bmiToolbar)
        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()
    }

    private fun displayUpButtonInToolbar() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
    }

    private fun setToolbarNavigationOnClickListener() {
        binding.bmiToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}