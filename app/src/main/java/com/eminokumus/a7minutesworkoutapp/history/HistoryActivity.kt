package com.eminokumus.a7minutesworkoutapp.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.WorkoutApp
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityHistoryBinding
import com.eminokumus.a7minutesworkoutapp.vo.WorkoutHistoryEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutHistoryDao = (application as WorkoutApp).database.historyDao()
        getWorkoutHistoryFromDatabase(workoutHistoryDao)


        setSupportActionBar(binding.historyToolbar)
        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

    }

    private fun displayUpButtonInToolbar() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
    }

    private fun setToolbarNavigationOnClickListener() {
        binding.historyToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getWorkoutHistoryFromDatabase(historyDao: WorkoutHistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllWorkouts().collect() { workoutHistoryList ->
                setViews(workoutHistoryList.isEmpty())
                setRecyclerViewAdapter(workoutHistoryList)
            }
        }
    }

    private fun setRecyclerViewAdapter(list: List<WorkoutHistoryEntity>)  {
        val adapter = HistoryAdapter(list)
        binding.historyRecycler.adapter = adapter
    }

    private fun setViews(isWorkoutHistoryListEmpty: Boolean) {
        if (isWorkoutHistoryListEmpty) {
            binding.historyRecycler.visibility = View.GONE
            binding.noDataText.visibility = View.VISIBLE
        } else {
            binding.historyRecycler.visibility = View.VISIBLE
            binding.noDataText.visibility = View.GONE
        }
    }
}