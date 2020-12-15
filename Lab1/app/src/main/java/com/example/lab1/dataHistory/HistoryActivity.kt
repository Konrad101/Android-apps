package com.example.lab1.dataHistory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.dataHistory.database.BmiDataHolder
import com.example.lab1.R
import com.example.lab1.dataHistory.database.HistoryRepository
import kotlinx.android.synthetic.main.activity_bmi_history.*

class HistoryActivity : AppCompatActivity() {
    private var historyList: List<BmiDataHolder> = arrayListOf()
    private lateinit var historyAdapter: HistoryAdapter
    private val itemsSpacing = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_history)
        historyList = HistoryRepository(application).getHistory()
        initRecyclerView()
        setData()

        title = "BMI history"
    }

    private fun setData() {
        historyAdapter.submitList(historyList)
    }

    private fun initRecyclerView() {
        historyRV.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            historyAdapter = HistoryAdapter()
            val topSpacing =
                TopSpacingHistoryItemDecoration(
                    itemsSpacing
                )
            addItemDecoration(topSpacing)
            adapter = historyAdapter
        }
    }
}