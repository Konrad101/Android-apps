package com.example.lab1.dataHistory.database

import android.app.Application

class HistoryRepository(application: Application) {
    private var historyDao: HistoryDao
    private var measurements: List<BmiDataHolder>

    init {
        val database: HistoryDatabase = HistoryDatabase.getInstance(application)
        historyDao = database.historyDao
        measurements = historyDao.getHistory()
    }

    fun insert(dataHolder: BmiDataHolder) {
        historyDao.insert(dataHolder)
    }

    fun clear() {
        historyDao.clear()
    }

    fun getHistory(): List<BmiDataHolder> {
        return measurements
    }
}