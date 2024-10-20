package com.eminokumus.a7minutesworkoutapp.vo

data class Exercise(
    val id: Int,
    val name: String,
    val image: Int,
    val isCompleted: Boolean = false,
    val isSelected: Boolean = false
)
