package com.example.lab1

import java.io.Serializable
import java.util.*

class BmiDataHolder(
    val height: Double,
    val weight: Double,
    private val americanUnits: Boolean = false,
    val bmi: Double = -1.0,
    val calculationDate: Date
) : Serializable {

    fun getHeightUnit(): String {
        return if (americanUnits) {
            "in"
        } else {
            "cm"
        }
    }

    fun getWeightUnit(): String {
        return if (americanUnits) {
            "lbs"
        } else {
            "kg"
        }
    }
}