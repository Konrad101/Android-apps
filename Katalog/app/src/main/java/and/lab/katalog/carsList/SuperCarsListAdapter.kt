package and.lab.katalog.carsList

import and.lab.katalog.R
import and.lab.katalog.carsContent.SuperCarsDataLoader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.supercar_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class SuperCarsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private lateinit var superCars: MutableList<CarListItem>
    private lateinit var superCarsToShow: MutableList<CarListItem>

    private lateinit var listViewModel: SuperCarsListViewModel

    inner class SuperCarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(carItem: CarListItem) {
            itemView.apply {
                brandTV.text = carItem.car.brand
                modelTV.text = carItem.car.model
                engineCapacityTV.text = carItem.car.engineCapacity.toString()
                engineTypeTV.text = carItem.car.engineType
                val carHorsepower: String = carItem.car.horsepower.toString() + " HP"
                horsePowerTV.text = carHorsepower
                carIV.setImageResource(SuperCarsDataLoader().getCarImageForID(carItem.id))

                setFavoriteButtonColor(carItem)
                favoriteBTN.setOnClickListener {
                    carItem.isFavorite = !carItem.isFavorite
                    setFavoriteButtonColor(carItem)
                    if (listViewModel.onlyFavoriteCars()) {
                        superCarsToShow = removeNotFavoriteItems(superCarsToShow)
                    }
                    listViewModel.setShownSuperCars(superCarsToShow)
                }

                setOnClickListener {
                    val carID = carItem.id
                    val bundle = bundleOf("carID" to carID)
                    it.findNavController()
                        .navigate(R.id.action_category_list_to_car_details, bundle)
                }
            }
        }

        private fun setFavoriteButtonColor(carItem: CarListItem) {
            itemView.apply {
                if (carItem.isFavorite) {
                    favoriteBTN.setBackgroundResource(R.drawable.ic_baseline_favorite_orange_24)
                } else {
                    favoriteBTN.setBackgroundResource(R.drawable.ic_baseline_favorite_border_shadow_24)
                }
            }
        }
    }

    fun removeAt(position: Int) {
        listViewModel.removeCar(superCarsToShow[position])
        notifyItemRemoved(position)
    }

    fun submitData(superCarsListViewModel: SuperCarsListViewModel) {
        listViewModel = superCarsListViewModel
        superCars = listViewModel.getCarsList()

        superCarsToShow = if (listViewModel.getShownSuperCars() == null) {
            superCars.toMutableList()
        } else {
            listViewModel.getShownSuperCars()!!
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.supercar_item, parent, false)
        return SuperCarViewHolder(v)
    }

    override fun getItemCount(): Int {
        return superCarsToShow.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SuperCarViewHolder -> {
                holder.bind(superCarsToShow[position])
            }
        }
    }

    override fun getFilter(): Filter {
        return carsFilter
    }

    private fun removeNotFavoriteItems(carItems: MutableList<CarListItem>): MutableList<CarListItem> {
        val favoriteCars = mutableListOf<CarListItem>()
        for (item in carItems) {
            if (item.isFavorite) {
                favoriteCars.add(item)
            }
        }
        return favoriteCars
    }

    private val carsFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            var filteredList: MutableList<CarListItem> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(superCars)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in superCars) {
                    if (item.car.brand.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            if (listViewModel.onlyFavoriteCars()) {
                filteredList = removeNotFavoriteItems(filteredList)
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            superCarsToShow.clear()
            superCarsToShow.addAll(results.values as Collection<CarListItem>)
            listViewModel.setShownSuperCars(superCarsToShow)
            notifyDataSetChanged()
        }
    }
}