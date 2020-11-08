package com.example.lab1.dataHistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.BmiDataHolder
import com.example.lab1.R
import kotlinx.android.synthetic.main.activity_bmi_history.*


class HistoryActivity : AppCompatActivity() {
    private var historyList: ArrayList<BmiDataHolder> = arrayListOf()
    private lateinit var historyAdapter: HistoryAdapter
    private val itemsSpacing = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_history)
        historyList = intent.getSerializableExtra("history_list") as ArrayList<BmiDataHolder>
        initRecyclerView()
        setData()

        val returnDataIntent = Intent().apply {
            putExtra("history_list", historyList)
        }
        setResult(RESULT_OK, returnDataIntent)
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