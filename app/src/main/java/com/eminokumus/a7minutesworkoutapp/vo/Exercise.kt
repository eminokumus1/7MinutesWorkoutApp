package com.eminokumus.a7minutesworkoutapp.vo

data class Exercise(
    val id: Int,
    val name: String,
    val image: Int,
    var isCompleted: Boolean = false,
    var isSelected: Boolean = false
)
