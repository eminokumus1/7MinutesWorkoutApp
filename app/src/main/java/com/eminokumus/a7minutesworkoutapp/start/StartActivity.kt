package com.eminokumus.a7minutesworkoutapp.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.a7minutesworkoutapp.exercise.ExerciseActivity
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityMainBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startFrameLayout.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
    }
}