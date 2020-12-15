package com.example.lab1.dataHistory.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "measurements_history")
class BmiDataHolder(
    val height: Double,
    val weight: Double,
    val americanUnits: Boolean = false,
    val bmi: Double = -1.0,
    val calculationDate: Date
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

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