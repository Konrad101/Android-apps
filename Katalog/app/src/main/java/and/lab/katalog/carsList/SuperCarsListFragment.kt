package and.lab.katalog.carsList

import and.lab.katalog.R
import and.lab.katalog.carsList.itemStyling.MarginCarItemDecoration
import and.lab.katalog.carsList.itemStyling.SwipeToDeleteCallback
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_cars_list.*


class SuperCarsListFragment : Fragment() {
    private lateinit var superCarsAdapter: SuperCarsListAdapter

    private lateinit var optionsMenu: Menu

    companion object {
        private var listViewModel: SuperCarsListViewModel? = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        optionsMenu = menu
        val menuInflater = activity?.menuInflater
        menuInflater?.inflate(R.menu.supercars_list_menu, menu)

        val onlyFavoriteItem = menu.findItem(R.id.action_favorite)
        onlyFavoriteItem.isChecked = listViewModel!!.onlyFavoriteCars()

        setCarBrandSearchListener()
    }

    private fun setCarBrandSearchListener() {
        val searchItem = optionsMenu.findItem(R.id.action_search)
        val carsSearchView: SearchView = searchItem.actionView as SearchView
        carsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                superCarsAdapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                val isChecked: Boolean = item.isChecked
                item.isChecked = !isChecked
                listViewModel?.setOnlyFavorite(item.isChecked)

                filterList()
                true
            }
            else -> true
        }
    }

    private fun filterList() {
        val searchItem = optionsMenu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        superCarsAdapter.filter.filter(searchView.query)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        initializeRecyclerView()

        val swipeHandler = object : SwipeToDeleteCallback(superCarsRV.context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = superCarsRV.adapter as SuperCarsListAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(superCarsRV)
        setData()

        listViewModel?.getShownCarsLiveData()?.observe(viewLifecycleOwner, Observer {
            setData()
        })
    }

    private fun initializeRecyclerView() {
        superCarsRV.apply {
            layoutManager = LinearLayoutManager(context)
            superCarsAdapter = SuperCarsListAdapter()
            val carItemDecoration =
                MarginCarItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.margin
                    )
                )
            addItemDecoration(carItemDecoration)
            adapter = superCarsAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        if (listViewModel == null) {
            listViewModel = ViewModelProvider(this).get(SuperCarsListViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragment_cars_list, container, false)
    }

    private fun setData() {
        listViewModel?.let { superCarsAdapter.submitData(it) }
    }
}