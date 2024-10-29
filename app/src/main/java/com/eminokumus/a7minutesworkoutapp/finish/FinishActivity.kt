package com.eminokumus.a7minutesworkoutapp.finish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityFinishBinding
import com.eminokumus.a7minutesworkoutapp.start.StartActivity

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.finishActivityToolbar)
        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

        binding.finishButton.setOnClickListener {
            finish()
        }
    }

    private fun displayUpButtonInToolbar() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setToolbarNavigationOnClickListener() {
        binding.finishActivityToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}