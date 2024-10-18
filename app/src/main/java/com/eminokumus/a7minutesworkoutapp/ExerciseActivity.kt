package com.eminokumus.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer? = null
    private val restTimeInMillis: Long = 10 * 1000
    private val restTimeInSeconds = restTimeInMillis.div(1000).toInt()
    private var restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.exerciseToolbar)

        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

        cancelRestTimer()
        setRestProgressBar(0)
        setRestTimer()
    }

    private fun setToolbarNavigationOnClickListener() {
        binding.exerciseToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun displayUpButtonInToolbar() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setRestProgressBar(restProgress: Int) {
        binding.progressBar.progress = restProgress
    }

    private fun setRestTimer() {
        restTimer = object : CountDownTimer(restTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                setRestProgressBar(restTimeInSeconds - restProgress)
                setTimerTextView((restTimeInSeconds - restProgress).toString())
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here now we will start the exercise",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }.start()
    }

    private fun setTimerTextView(timeLeft: String) {
        binding.timerText.text = timeLeft
    }

    private fun cancelRestTimer(){
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelRestTimer()
    }
}
