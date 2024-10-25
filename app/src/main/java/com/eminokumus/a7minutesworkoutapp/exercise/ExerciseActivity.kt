package com.eminokumus.a7minutesworkoutapp.exercise

import android.app.Dialog
import android.content.Intent
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
import com.eminokumus.a7minutesworkoutapp.FinishActivity
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import com.eminokumus.a7minutesworkoutapp.databinding.BackConfirmationDialogBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var adapter: ExerciseStatusAdapter

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
        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

        observeLiveData()



        cancelTimer(restTimer)
        setRestProgressBarProgress(0)
        setRestTimer()
        setupExerciseStatusRecyclerAdapter()
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

    private fun setupExerciseStatusRecyclerAdapter() {
        adapter = ExerciseStatusAdapter(viewModel.getExerciseList())
        binding.exerciseStatusRecycler.adapter = adapter
    }

    private fun displayCustomDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = BackConfirmationDialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.yesButton.setOnClickListener {
            this@ExerciseActivity.finish()
            displayDimBackground()
            customDialog.dismiss()
        }
        dialogBinding.noButton.setOnClickListener {
            hideDimBackground()
            customDialog.dismiss()
        }
        customDialog.window?.setBackgroundDrawableResource(R.drawable.rounded_bg)
        customDialog.show()
    }

    private fun displayDimBackground(){
        binding.dimBackgroundView.visibility = View.VISIBLE
    }
    private fun hideDimBackground(){
        binding.dimBackgroundView.visibility = View.GONE
    }

    private fun setToolbarNavigationOnClickListener() {
        binding.exerciseToolbar.setNavigationOnClickListener {
            displayCustomDialogForBackButton()
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
                viewModel.setCurrentExerciseSelected(true)
                adapter.notifyDataSetChanged()
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
                viewModel.setCurrentExerciseSelected(false)
                viewModel.setCurrentExerciseCompleted(true)
                adapter.notifyDataSetChanged()
                if (viewModel.hasExerciseListNext()) {
                    setRestViews()
                    cancelTimer(exerciseTimer)
                    cancelTimer(restTimer)
                    setRestTimer()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Workout completed", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
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
        binding.exerciseFrameLayout.visibility = View.INVISIBLE
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
        binding.restFrameLayout.visibility = View.INVISIBLE
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
