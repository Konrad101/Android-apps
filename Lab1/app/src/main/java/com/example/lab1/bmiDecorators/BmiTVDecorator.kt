package com.example.lab1.bmiDecorators

import android.graphics.Color
import android.widget.TextView
import com.example.lab1.BmiResultActivity
import com.example.lab1.bmi.BmiBounds

class BmiTVDecorator(private val bmiTextView: TextView, private val bmiValue: Double) {
    private val noneOperationColor: String = "#000000"
    private val greenColorHex: String = "#33FF33"
    private val redColorHex: String = "#FF3333"
    private val blueColorHex: String = "#3333FF"

    private val smallBmiDescription: String = "You better gain some weight!"
    private val greatBmiDescription: String = "Wow! You're in great shape!"
    private val bigBmiDescription: String = "You're too fat! Try to eat less!"

    fun colorBmiValue() {
        val colorStr = when {
            bmiValue == 0.0 -> {
                noneOperationColor
            }
            bmiValue < BmiBounds.GREAT_BMI_LOWER_BOUND -> {
                blueColorHex
            }
            bmiValue > BmiBounds.GREAT_BMI_UPPER_BOUND -> {
                redColorHex
            }
            else -> {
                greenColorHex
            }
        }
        bmiTextView.setTextColor(Color.parseColor(colorStr))
    }

    fun addComment() {
        when {
            bmiValue < BmiBounds.GREAT_BMI_LOWER_BOUND -> {
                bmiTextView.text = smallBmiDescription
            }
            bmiValue > BmiBounds.GREAT_BMI_UPPER_BOUND -> {
                bmiTextView.text = bigBmiDescription
            }
            else -> {
                bmiTextView.text = greatBmiDescription
            }
        }
    }
}