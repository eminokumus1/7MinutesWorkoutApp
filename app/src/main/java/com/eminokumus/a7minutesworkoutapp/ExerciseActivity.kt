package com.eminokumus.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer? = null
    private val restTimeInMillis: Long = 10 * 1000
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private val exerciseTimeInMillis: Long = 30 * 1000
    private var exerciseProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.exerciseToolbar)

        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

        cancelTimer(restTimer)
        setRestProgressBarProgress(0)
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

    private fun setRestProgressBarProgress(newRestProgress: Int) {
        binding.restProgressBar.progress = newRestProgress
    }

    private fun setRestTimer() {
        setTitleTextView(resources.getString(R.string.get_ready_for))
        restTimer = object : CountDownTimer(restTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                val restTimeInSeconds = restTimeInMillis.div(1000).toInt()
                setRestProgressBarProgress(restTimeInSeconds - restProgress)
                setTimerTextView((restTimeInSeconds - restProgress).toString())
            }

            override fun onFinish() {
                displayExerciseProgressBar()
                cancelTimer(exerciseTimer)
                setExerciseTimer()

            }

        }.start()
    }

    private fun setExerciseProgressBarProgress(newExerciseProgress: Int){
        binding.exerciseProgressBar.progress = newExerciseProgress
    }

   private fun setExerciseTimer() {
       setTitleTextView("Exercise Name")
        exerciseTimer = object : CountDownTimer(exerciseTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                val exerciseTimeInSeconds = exerciseTimeInMillis.div(1000).toInt()
                setExerciseProgressBarProgress(exerciseTimeInSeconds - exerciseProgress)
                setTimerTextView((exerciseTimeInSeconds - exerciseProgress).toString())
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Exercise done, rest now",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }.start()
    }

    private fun setTimerTextView(timeLeft: String) {
        binding.timerText.text = timeLeft
    }
    private fun setTitleTextView(newTitle: String){
        binding.TitleText.text = newTitle
    }

    private fun displayRestProgressBar(){
        binding.restProgressBar.visibility = View.VISIBLE
        binding.exerciseProgressBar.visibility = View.GONE
    }
    private fun displayExerciseProgressBar(){
        binding.restProgressBar.visibility = View.GONE
        binding.exerciseProgressBar.visibility = View.VISIBLE
    }

    private fun cancelTimer(timer: CountDownTimer?) {
        if (timer != null) {
            timer.cancel()
            restProgress = 0
            exerciseProgress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer(restTimer)
        cancelTimer(exerciseTimer)
    }
}
