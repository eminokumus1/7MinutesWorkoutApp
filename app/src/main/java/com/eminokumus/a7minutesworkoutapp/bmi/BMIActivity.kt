package com.eminokumus.a7minutesworkoutapp.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.eminokumus.a7minutesworkoutapp.R
import com.eminokumus.a7minutesworkoutapp.databinding.ActivityBmiBinding


enum class UnitType() {
    METRIC, US
}

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding

    private var currentUnitType = UnitType.METRIC
    private var bmi : BMI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.bmiToolbar)
        displayUpButtonInToolbar()
        setToolbarNavigationOnClickListener()

        binding.unitsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.metricUnitsRadioButton) {
                currentUnitType = UnitType.METRIC
                displayMetricUnitViews()
            } else {
                currentUnitType = UnitType.US
                displayUSUnitViews()
            }
        }

        setCalculateButtonOnClickListener()
    }

    private fun setCalculateButtonOnClickListener() {
        binding.calculateButton.setOnClickListener {
            if (validateInputs()) {
                setBMI()
                bmi?.calculateBMI()
                displayResultLinearLayout()
                setResultTexts()
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun displayMetricUnitViews() {
        binding.run {
            weightInputLayout.hint = resources.getString(R.string.weight_hint_metric)
            heightMetricInputLayout.visibility = View.VISIBLE
        }
        hideUSUnitLayouts()
        clearUSUnitEditTexts()
        hideResultLinearLayout()
    }

    private fun displayUSUnitViews() {
        binding.run {
            weightInputLayout.hint = resources.getString(R.string.weight_hint_us)
            heightMetricInputLayout.visibility = View.INVISIBLE
            heightFeetInputLayout.visibility = View.VISIBLE
            heightInchInputLayout.visibility = View.VISIBLE
        }
        clearMetricUnitEditTexts()
        hideResultLinearLayout()
    }

    private fun clearUSUnitEditTexts() {
        binding.run {
            weightInputText.text?.clear()
            heightFeetInputText.text?.clear()
            heightInchInputText.text?.clear()
        }
    }

    private fun clearMetricUnitEditTexts() {
        binding.run {
            weightInputText.text?.clear()
            heightMetricInputText.text?.clear()
        }
    }

    private fun hideUSUnitLayouts() {
        binding.run {
            heightFeetInputLayout.visibility = View.INVISIBLE
            heightInchInputLayout.visibility = View.INVISIBLE
        }
    }

    private fun displayResultLinearLayout() {
        binding.resultLinearLayout.visibility = View.VISIBLE
    }

    private fun hideResultLinearLayout() {
        binding.resultLinearLayout.visibility = View.INVISIBLE
    }

    private fun setResultTexts() {
        binding.run {
            bmi?.let {
                bmiValueText.text = it.toString()
                val bmiType = it.getBMIType()
                bmiTypeText.text = bmiType.type
                bmiDescriptionText.text = bmiType.description
            }

        }
    }


    private fun setBMI() {
        bmi = if (currentUnitType == UnitType.METRIC) {
            MetricBMI(
                binding.weightInputText.text.toString().toFloat(),
                binding.heightMetricInputText.text.toString().toFloat()
            )

        } else {
            USBMI(
                binding.weightInputText.text.toString().toFloat(),
                binding.heightFeetInputText.text.toString().toFloat(),
                binding.heightInchInputText.text.toString().toFloat()
            )
        }
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

    private fun validateInputs() = if (currentUnitType == UnitType.METRIC) {
        validateInputsForMetricUnits()
    } else {
        validateInputsForUSUnits()

    }

    private fun validateInputsForMetricUnits() =
        !(binding.weightInputText.text.toString().isEmpty() ||
                binding.heightMetricInputText.text.toString().isEmpty())

    private fun validateInputsForUSUnits() =
        !(binding.weightInputText.text.toString().isEmpty() ||
                binding.heightFeetInputText.text.toString().isEmpty() ||
                binding.heightInchInputText.text.toString().isEmpty())
}