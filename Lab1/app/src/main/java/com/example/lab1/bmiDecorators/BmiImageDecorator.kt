package com.example.lab1.bmiDecorators

import android.widget.ImageView
import com.example.lab1.BmiResultActivity
import com.example.lab1.R
import com.example.lab1.bmi.BmiBounds

class BmiImageDecorator(private val imageView: ImageView, private val bmiValue: Double) {
    fun changeImage() {
        when {
            bmiValue < BmiBounds.GREAT_BMI_LOWER_BOUND -> {
                imageView.setImageResource(R.drawable.skinny_bmi_image)
            }
            bmiValue > BmiBounds.GREAT_BMI_UPPER_BOUND -> {
                imageView.setImageResource(R.drawable.fat_bmi_image)
            }
            else -> {
                imageView.setImageResource(R.drawable.great_bmi_image)
            }
        }
    }
}