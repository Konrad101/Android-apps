package com.example.lab1.bmi

class BmiForCmKg(var height: Double, var weight: Double) : Bmi {
    override fun count(): Double {
        return if (weight < 0 || height < 0 || height == 0.0) {
            0.0
        } else {
            height /= 100
            weight / (height * height)
        }
    }
}