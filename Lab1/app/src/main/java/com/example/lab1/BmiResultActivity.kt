package com.example.lab1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.bmiDecorators.BmiImageDecorator
import com.example.lab1.bmiDecorators.BmiTVDecorator
import kotlinx.android.synthetic.main.activity_bmi_result.*

class BmiResultActivity : AppCompatActivity() {
    private var bmiResult: Double = 0.0

    val greatBmiLowerBound: Double = 18.5
    val greatBmiUpperBound: Double = 24.99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_result)

        bmiResult = intent.getDoubleExtra("bmiResult", 0.0)
        bmiValueTV.text = bmiResult.toString()
        setBmiComponents()
    }

    private fun setBmiComponents() {
        BmiTVDecorator(bmiValueTV, bmiResult).colorBmiValue()
        BmiTVDecorator(commentTV, bmiResult).addComment()
        BmiImageDecorator(bmiBodyTypeImage, bmiResult).changeImage()
    }
}