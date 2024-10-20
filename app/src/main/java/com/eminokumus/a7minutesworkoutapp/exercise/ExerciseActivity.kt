package com.eminokumus.a7minutesworkoutapp.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var viewModel: ExerciseViewModel

    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ExerciseViewModel()

        setSupportActionBar(binding.exerciseToolbar)

        observeLiveData()

        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

        cancelTimer(restTimer)
        setRestProgressBarProgress(0)
        setRestTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer(restTimer)
        cancelTimer(exerciseTimer)
    }

    private fun observeLiveData() {
        viewModel.currentExercise.observe(this) { newExercise ->
            binding.exerciseImage.setImageResource(newExercise.image)
            binding.exerciseNameText.text = newExercise.name

        }
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
        restTimer = object : CountDownTimer(viewModel.getRestTime(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.increaseRestProgress()
                val restTimeInSeconds = viewModel.getRestTime().div(1000).toInt()
                setRestProgressBarProgress(restTimeInSeconds - viewModel.getRestProgress())
                setTimerTextView((restTimeInSeconds - viewModel.getRestProgress()).toString())
            }

            override fun onFinish() {
                viewModel.updateExercise()
                displayExerciseViews()
                cancelTimer(exerciseTimer)
                cancelTimer(restTimer)
                setExerciseTimer()

            }

        }.start()
    }

    private fun setExerciseProgressBarProgress(newExerciseProgress: Int) {
        binding.exerciseProgressBar.progress = newExerciseProgress
    }

    private fun setExerciseTimer() {
        exerciseTimer = object : CountDownTimer(viewModel.getExerciseTime(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.increaseExerciseProgress()
                val exerciseTimeInSeconds = viewModel.getExerciseTime().div(1000).toInt()
                setExerciseProgressBarProgress(exerciseTimeInSeconds - viewModel.getExerciseProgress())
                setTimerTextView((exerciseTimeInSeconds - viewModel.getExerciseProgress()).toString())
            }

            override fun onFinish() {
                if (viewModel.hasExerciseListNext()) {
                    displayRestViews()
                    cancelTimer(exerciseTimer)
                    cancelTimer(restTimer)
                    setRestTimer()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Workout completed", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }.start()

    }


    private fun setTimerTextView(timeLeft: String) {
        binding.timerText.text = timeLeft
    }

    private fun displayRestViews() {
        displayRestProgressBar()
        displayTitleTextView()
        hideExerciseImage()
    }

    private fun displayRestProgressBar() {
        binding.restProgressBar.visibility = View.VISIBLE
        binding.exerciseProgressBar.visibility = View.GONE
    }

    private fun displayTitleTextView() {
        binding.titleText.visibility = View.VISIBLE
        binding.exerciseNameText.visibility = View.GONE
    }

    private fun displayExerciseViews() {
        displayExerciseProgressBar()
        displayExerciseNameTextView()
        displayExerciseImage()
    }

    private fun displayExerciseProgressBar() {
        binding.restProgressBar.visibility = View.GONE
        binding.exerciseProgressBar.visibility = View.VISIBLE
    }

    private fun displayExerciseNameTextView() {
        binding.titleText.visibility = View.GONE
        binding.exerciseNameText.visibility = View.VISIBLE
    }

    private fun displayExerciseImage() {
        binding.exerciseImage.visibility = View.VISIBLE
    }

    private fun hideExerciseImage() {
        binding.exerciseImage.visibility = View.GONE
    }

    private fun cancelTimer(timer: CountDownTimer?) {
        if (timer != null) {
            timer.cancel()
            viewModel.resetRestProgress()
            viewModel.resetExerciseProgress()
        }
    }


}
