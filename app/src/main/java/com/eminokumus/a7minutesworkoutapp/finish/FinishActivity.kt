package com.eminokumus.a7minutesworkoutapp.finish

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.eminokumus.a7minutesworkoutapp.WorkoutApp
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityFinishBinding
import com.eminokumus.a7minutesworkoutapp.history.WorkoutHistoryDao
import com.eminokumus.a7minutesworkoutapp.vo.WorkoutHistoryEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val workoutHistoryDao = (application as WorkoutApp).database.historyDao()


        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.finishActivityToolbar)
        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

        addWorkoutToDatabase(workoutHistoryDao)


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

    private fun addWorkoutToDatabase(workoutHistoryDao: WorkoutHistoryDao){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        val simpleDataFormat = SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.getDefault())
        val date = simpleDataFormat.format(dateTime)

        lifecycleScope.launch {
            workoutHistoryDao.insert(WorkoutHistoryEntity(date))
        }

    }
}