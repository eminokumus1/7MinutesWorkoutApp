package com.eminokumus.a7minutesworkoutapp.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.a7minutesworkoutapp.Constants
import com.eminokumus.a7minutesworkoutapp.vo.Exercise

class ExerciseViewModel : ViewModel() {
    private val restTimeInMillis: Long = 1 * 1000
    private var restProgress = 0


    private val exerciseTimeInMillis: Long = 1 * 1000
    private var exerciseProgress = 0

    private val exerciseList = Constants.getExerciseList()
    private val _currentExercise = MutableLiveData<Exercise>()
    val currentExercise: LiveData<Exercise> get() = _currentExercise

    private var _nextExerciseName = MutableLiveData<String>()
    val nextExerciseName: LiveData<String> get() = _nextExerciseName

    private var currentExercisePosition = -1

    init {
        _currentExercise.value = exerciseList[0]
        _nextExerciseName.value = exerciseList[0].name
    }

    fun getExerciseList(): ArrayList<Exercise> {
        return exerciseList
    }

    fun getRestTime(): Long {
        return restTimeInMillis
    }

    fun getExerciseTime(): Long {
        return exerciseTimeInMillis
    }

    fun getRestProgress(): Int {
        return restProgress
    }

    fun increaseRestProgress() {
        restProgress++
    }

    fun resetRestProgress() {
        restProgress = 0
    }

    fun getExerciseProgress(): Int {
        return exerciseProgress
    }

    fun increaseExerciseProgress() {
        exerciseProgress++
    }

    fun resetExerciseProgress() {
        exerciseProgress = 0
    }

    fun hasExerciseListNext(): Boolean {
        return currentExercisePosition < exerciseList.size - 1
    }

    fun updateExercise() {
        currentExercisePosition++
        if (currentExercisePosition < exerciseList.size) {
            _currentExercise.value = exerciseList[currentExercisePosition]
            updateNextExerciseName()
        }
    }

    fun updateNextExerciseName() {
        if (hasExerciseListNext()) {
            _nextExerciseName.value = exerciseList[currentExercisePosition + 1].name
        }
    }

    fun setCurrentExerciseSelected(isSelected: Boolean){
        _currentExercise.value?.isSelected = isSelected
    }
    fun setCurrentExerciseCompleted(isCompleted: Boolean){
        _currentExercise.value?.isCompleted = isCompleted
    }
}