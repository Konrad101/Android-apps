package com.example.lab1.bmi

class BmiForInLbs(var height: Double, var weight: Double) : Bmi {
    override fun count(): Double {
        return if (weight < 0 || height < 0 || height == 0.0) {
            0.0
        } else {
            (weight * 703) / (height * height)
        }
    }
}