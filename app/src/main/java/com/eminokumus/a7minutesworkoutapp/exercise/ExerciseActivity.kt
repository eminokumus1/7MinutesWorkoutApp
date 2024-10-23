package com.eminokumus.a7minutesworkoutapp.exercise

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var viewModel: ExerciseViewModel

    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null

    private var textToSpeech: TextToSpeech? = null
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeTextToSpeech()
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
        cancelPlayer()
        destroyTextToSpeech()
    }

    private fun destroyTextToSpeech() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
    }

    private fun initializeTextToSpeech() {
        textToSpeech = TextToSpeech(this, this)
    }

    private fun observeLiveData() {
        viewModel.currentExercise.observe(this) { newExercise ->
            binding.exerciseImage.setImageResource(newExercise.image)
            binding.exerciseNameText.text = newExercise.name
        }
        viewModel.nextExerciseName.observe(this) { upcomingExerciseName ->
            binding.upComingExerciseNameText.text = upcomingExerciseName

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
        playSound(R.raw.press_start)

        restTimer = object : CountDownTimer(viewModel.getRestTime(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.increaseRestProgress()
                val restTimeInSeconds = viewModel.getRestTime().div(1000).toInt()
                setRestProgressBarProgress(restTimeInSeconds - viewModel.getRestProgress())
                val timeLeft = (restTimeInSeconds - viewModel.getRestProgress()).toString()
                setTimerTextView(binding.restTimerText, timeLeft)
            }

            override fun onFinish() {
                viewModel.updateExercise()
                setExerciseViews()
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
                val timeLeft = (exerciseTimeInSeconds - viewModel.getExerciseProgress()).toString()
                setTimerTextView(binding.exerciseTimerText, timeLeft)
            }

            override fun onFinish() {
                if (viewModel.hasExerciseListNext()) {
                    setRestViews()
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


    private fun setTimerTextView(timerTextView: TextView, timeLeft: String) {
        timerTextView.text = timeLeft
    }

    private fun setRestViews() {
        displayRestFrameLayout()
        displayTitleTextView()
        hideExerciseImage()
        displayUpcomingExercise()
    }

    private fun displayRestFrameLayout() {
        binding.restFrameLayout.visibility = View.VISIBLE
        binding.exerciseFrameLayout.visibility = View.GONE
    }

    private fun displayTitleTextView() {
        binding.titleText.visibility = View.VISIBLE
        binding.exerciseNameText.visibility = View.GONE
    }

    private fun displayUpcomingExercise() {
        binding.upComingTitleText.visibility = View.VISIBLE
        binding.upComingExerciseNameText.visibility = View.VISIBLE
    }

    private fun hideUpcomingExercise() {
        binding.upComingTitleText.visibility = View.GONE
        binding.upComingExerciseNameText.visibility = View.GONE
    }

    private fun setExerciseViews() {
        displayExerciseFrameLayout()
        displayExerciseNameTextView()
        displayExerciseImage()
        hideUpcomingExercise()
        speakOut(binding.exerciseNameText.text.toString())
    }

    private fun displayExerciseFrameLayout() {
        binding.restFrameLayout.visibility = View.GONE
        binding.exerciseFrameLayout.visibility = View.VISIBLE
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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeechError", "The Language specified is not supported!")
            }
        } else {
            Log.e("TextToSpeechError", "Initialization Failed!")
            val sound = R.raw.press_start
        }
    }

    private fun speakOut(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun playSound(soundRaw: Int) {
        try {
            val soundURI =
                Uri.parse("android.resource://com.eminokumus.a7minutesworkoutapp/$soundRaw")
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun cancelPlayer(){
        if (player != null){
            player!!.stop()
        }
    }


}
