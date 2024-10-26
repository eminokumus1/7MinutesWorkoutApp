package com.eminokumus.a7minutesworkoutapp.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

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

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding

    private var bmi = 0f
    private var bmiValue = ""
    private lateinit var bmiType : BMIType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.bmiToolbar)
        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

        binding.calculateButton.setOnClickListener {
            if (validateInputs()) {
                bmi = calculateBMI()
                bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
                bmiType = findBMIType()
                displayResultLinearLayout()
                setResultTexts()
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun displayResultLinearLayout(){
        binding.resultLinearLayout.visibility = View.VISIBLE
    }

    private fun setResultTexts() {
        binding.bmiValueText.text = bmiValue
        binding.bmiTypeText.text = bmiType.type
        binding.bmiDescriptionText.text = bmiType.description
    }

    private fun findBMIType(): BMIType {
        return if (bmi < 15f) {
            BMIType.VERY_SEVERELY_UNDERWEIGHT
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            BMIType.VERY_UNDERWEIGHT
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            BMIType.UNDERWEIGHT
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            BMIType.NORMAL
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            BMIType.OVERWEIGHT
        } else {
            BMIType.OBESE
        }
    }

    private fun calculateBMI(): Float {
        val weightInKg = binding.weightInputText.text.toString().toFloat()
        val heightInMeter = binding.heightInputText.text.toString().toFloat() / 100
        val bmi = weightInKg / (heightInMeter * heightInMeter)

        return bmi
    }

    private fun displayUpButtonInToolbar() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
    }

    private fun setToolbarNavigationOnClickListener() {
        binding.bmiToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun validateInputs(): Boolean {
        val isValid = if (binding.weightInputText.text.toString().isEmpty()) {
            false
        } else if (binding.heightInputText.text.toString().isEmpty()) {
            false
        } else {
            true
        }
        return isValid
    }
}