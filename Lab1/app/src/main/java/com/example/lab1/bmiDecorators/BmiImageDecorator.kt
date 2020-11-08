package com.example.lab1.bmiDecorators

import android.widget.ImageView
import com.example.lab1.BmiResultActivity
import com.example.lab1.R

class BmiImageDecorator(private val imageView: ImageView, private val bmiValue: Double) {
    fun changeImage() {
        when {
            bmiValue < BmiResultActivity().greatBmiLowerBound -> {
                imageView.setImageResource(R.drawable.skinny_bmi_image)
            }
            bmiValue > BmiResultActivity().greatBmiUpperBound -> {
                imageView.setImageResource(R.drawable.fat_bmi_image)
            }
            else -> {
                imageView.setImageResource(R.drawable.great_bmi_image)
            }
        }
    }
}