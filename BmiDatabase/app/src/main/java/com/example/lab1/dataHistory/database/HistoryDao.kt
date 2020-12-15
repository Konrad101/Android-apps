package com.example.lab1.dataHistory.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert
    fun insert(holder: BmiDataHolder)

    @Query("SELECT * FROM measurements_history")
    fun getHistory(): List<BmiDataHolder>

    @Query("DELETE FROM measurements_history")
    fun clear()
}