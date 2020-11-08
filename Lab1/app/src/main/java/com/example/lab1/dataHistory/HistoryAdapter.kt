package com.example.lab1.dataHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.BmiDataHolder
import com.example.lab1.R
import com.example.lab1.bmiDecorators.BmiTVDecorator
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var history: List<BmiDataHolder>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return history.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryViewHolder -> {
                holder.bind(history[position])
            }
        }
    }

    fun submitList(historyList: List<BmiDataHolder>) {
        history = historyList
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bmiHolder: BmiDataHolder) {
            itemView.apply {
                bmiHistoryValueTV.text = String.format("%.2f", bmiHolder.bmi)
                BmiTVDecorator(bmiHistoryValueTV, bmiHistoryValueTV.text.toString().toDouble()).colorBmiValue()
                val weightWithUnit = bmiHolder.weight.toString() + ' ' + bmiHolder.getWeightUnit()
                val heightWithUnit = bmiHolder.height.toString() + ' ' + bmiHolder.getHeightUnit()
                weightValueTV.text = weightWithUnit
                heightValueTV.text = heightWithUnit
                bmiDateTV.text = bmiHolder.calculationDate.toString()
            }
        }
    }
}