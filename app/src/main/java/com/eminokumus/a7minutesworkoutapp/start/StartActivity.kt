package com.eminokumus.a7minutesworkoutapp.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.a7minutesworkoutapp.bmi.BMIActivity
import com.eminokumus.a7minutesworkoutapp.exercise.ExerciseActivity
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setStartFrameLayoutOnClickListener()
        setBMIFrameLayoutOnClickListener()
    }

    private fun setBMIFrameLayoutOnClickListener() {
        binding.bmiFrameLayout.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setStartFrameLayoutOnClickListener() {
        binding.startFrameLayout.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
    }
}