package com.eminokumus.a7minutesworkoutapp.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.a7minutesworkoutapp.Constants
import com.eminokumus.a7minutesworkoutapp.vo.Exercise

class ExerciseViewModel : ViewModel(){
    private val restTimeInMillis: Long = 10 * 1000
    private var restProgress = 0


    private val exerciseTimeInMillis: Long = 30 * 1000
    private var exerciseProgress = 0

    private val exerciseList = Constants.getExerciseList()
    private val _currentExercise = MutableLiveData<Exercise>()
    private val currentExercise: LiveData<Exercise> get() =  _currentExercise
    private var currentExercisePosition = -1


    fun getRestTime(): Long{
        return restTimeInMillis
    }
    fun getExerciseTime(): Long{
        return exerciseTimeInMillis
    }

    fun getRestProgress(): Int{
        return restProgress
    }

    fun increaseRestProgress(){
        restProgress++
    }
    fun resetRestProgress(){
        restProgress = 0
    }
    fun getExerciseProgress(): Int{
        return exerciseProgress
    }

    fun increaseExerciseProgress(){
        exerciseProgress++
    }
    fun resetExerciseProgress(){
        exerciseProgress = 0
    }

    fun updateExercise(){
        currentExercisePosition++
        _currentExercise.value = exerciseList[currentExercisePosition]
    }
}