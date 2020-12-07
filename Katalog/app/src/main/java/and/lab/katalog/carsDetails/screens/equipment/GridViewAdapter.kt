package and.lab.katalog.carsDetails.screens.equipment

import and.lab.katalog.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GridViewAdapter(
    val context: Context,
    private val inflater: LayoutInflater,
    private val equipmentList: List<String>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val convView: View = when (convertView) {
            null -> inflater.inflate(R.layout.equipment_row_item, null)
            else -> convertView
        }
        val equipmentTextView: TextView = convView.findViewById(R.id.equipmentTV)
        val equipment = "- " + equipmentList[position]
        equipmentTextView.text = equipment

        return convView
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return equipmentList.size
    }
}