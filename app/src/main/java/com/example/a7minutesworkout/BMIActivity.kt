package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    val US_UNIT_VIEW = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(toolbar_bmi_activity)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "CALCULATE BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        // Dislpay the right metric unit
        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView.equals(METRIC_UNIT_VIEW)) {
                calculateMetricBMI()
            } else {
                calculateUSBMI()
            }
        }

        // on radioGroup press change metric unit
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }
    }


    private fun calculateMetricBMI() {
        if (validateMetricUnits()) {
            val heightValue: Float = etMetricUnitHeight.text.toString().toFloat() / 100
            val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()
            val lorentzIdealWeightH: Float =
                ((heightValue * 100) - 100) - (((heightValue * 100) - 150) / 4)

            val lorentzIdealWeightF: Float =
                heightValue - 100 - ((heightValue - 150) / 2.5f)
            val bmi = weightValue / (heightValue * heightValue)
            displayBMIResult(bmi, lorentzIdealWeightH)

        } else {
            Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        } else if (etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    /**
     * Function is used to display the result of METRIC UNITS.
     */
    private fun displayBMIResult(bmi: Float, idealWeight: Float = 0.0f) {

        val bmiLabel: String
        val bmiDescription: String

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDiplayBMIResult.visibility = View.VISIBLE
        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE
        tvIdealWeight.visibility = View.VISIBLE

        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView
        if (idealWeight.equals(0.0f)) {
            tvIdealWeight.visibility = View.GONE
        } else {
            tvIdealWeight.visibility = View.VISIBLE
            tvIdealWeight.text = "your ideal weight is  $idealWeight kg (Lorentz)"
        }
    }

    private fun calculateUSBMI() {
        if (validateUSUnits()) {
            val usUnitHeightValueFeet: String =
                etUsUnitHeightFeet.text.toString() // Height Feet value entered in EditText component.
            val usUnitHeightValueInch: String =
                etUsUnitHeightInch.text.toString() // Height Inch value entered in EditText component.
            val usUnitWeightValue: Float = etUsUnitWeight.text.toString()
                .toFloat() // Weight value entered in EditText component.

            // Here the Height Feet and Inch values are merged and multiplied by 12 for converting it to inches.
            val heightValue =
                usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

            // This is the Formula for US UNITS result.
            // Reference Link : https://www.cdc.gov/healthyweight/assessing/bmi/childrens_bmi/childrens_bmi_formula.html
            val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))
            displayBMIResult(bmi) // Displaying the result into UI
        } else {
            Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
        }
    }


    private fun validateUSUnits(): Boolean {
        var isValid = true

        when {
            etUsUnitWeight.text.toString().isEmpty() -> {
                isValid = false
            }
            etUsUnitHeightFeet.text.toString().isEmpty() -> {
                isValid = false
            }
            etUsUnitHeightInch.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }

    private fun makeVisibleMetricUnitsView() {

        currentVisibleView = METRIC_UNIT_VIEW
        tilMetricUnitWeight.visibility = View.VISIBLE
        tilMetricUnitHeight.visibility = View.VISIBLE

        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        llUsUnitsHeight.visibility = View.GONE
        tilUsUnitWeight.visibility = View.GONE
        llUsUnitsView.visibility = View.GONE

        llDiplayBMIResult.visibility = View.GONE

    }

    private fun makeVisibleUSUnitsView() {

        currentVisibleView = US_UNIT_VIEW
        tilMetricUnitWeight.visibility = View.GONE
        tilMetricUnitHeight.visibility = View.GONE

        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightFeet.text!!.clear()

        llUsUnitsView.visibility = View.VISIBLE
        llUsUnitsHeight.visibility = View.VISIBLE
        tilUsUnitWeight.visibility = View.VISIBLE


        llDiplayBMIResult.visibility = View.GONE
    }

}