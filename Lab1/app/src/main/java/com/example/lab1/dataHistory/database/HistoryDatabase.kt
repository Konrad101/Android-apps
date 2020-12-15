package com.example.lab1.dataHistory.database

import android.content.Context
import androidx.room.*

@Database(entities = [BmiDataHolder::class], version = 1)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract val historyDao: HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "measurements_history"
                    )
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}