package com.eminokumus.a7minutesworkoutapp.bmi

enum class BMIType(val type: String, val description: String) {
    VERY_SEVERELY_UNDERWEIGHT(
        "Very severely underweight",
        "Oops! You really need to take better care of yourself! Eat more!"
    ),
    VERY_UNDERWEIGHT(
        "Very underweight",
        "Oops!You really need to take better care of yourself! Eat more!"
    ),
    UNDERWEIGHT(
        "Underweight",
        "Oops!You really need to take better care of yourself! Eat more!"
    ),
    NORMAL(
        "Normal",
        "Congratulations! You are in a good shape!"
    ),
    OVERWEIGHT(
        "Overweight",
        "Oops! You really need to take care of your yourself! Workout maybe!"
    ),
    OBESE(
        "OBESE",
        "Oops! You really need to take care of your yourself! Workout maybe!"
    )

}

abstract class BMI {
    abstract var value: Float
        protected set

    abstract fun calculateBMI(): Float

    fun getBMIType() = if (value < 15f) {
        BMIType.VERY_SEVERELY_UNDERWEIGHT
    } else if (value.compareTo(15f) > 0 && value.compareTo(16f) <= 0) {
        BMIType.VERY_UNDERWEIGHT
    } else if (value.compareTo(16f) > 0 && value.compareTo(18.5f) <= 0) {
        BMIType.UNDERWEIGHT
    } else if (value.compareTo(18.5f) > 0 && value.compareTo(25f) <= 0) {
        BMIType.NORMAL
    } else if (value.compareTo(25f) > 0 && value.compareTo(30f) <= 0) {
        BMIType.OVERWEIGHT
    } else {
        BMIType.OBESE
    }

    override fun toString(): String {
        return (value * 100).toInt().toFloat().div(100).toString()
    }
}

class MetricBMI(val weight: Float, val height: Float) : BMI() {
    override var value = 0f
    override fun calculateBMI(): Float {
        val heightInMeter = height / 100
        value = weight / (heightInMeter * heightInMeter)
        return value
    }
}

class USBMI(val weight: Float, val feet: Float, val inch: Float) : BMI() {
    override var value = 0f
    override fun calculateBMI(): Float {
        val height = 12 * feet + inch
        value = 703 * weight / (height * height)
        return value
    }

}