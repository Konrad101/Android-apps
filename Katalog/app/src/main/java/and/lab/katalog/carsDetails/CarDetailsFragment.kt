package and.lab.katalog.carsDetails

import and.lab.katalog.R
import and.lab.katalog.carsContent.SuperCarsDataLoader
import and.lab.katalog.carsDetails.screens.CarDescriptionScreen
import and.lab.katalog.carsDetails.screens.equipment.CarEquipmentScreen
import and.lab.katalog.carsDetails.screens.CarGalleryScreen
import and.lab.katalog.carsDetails.screens.DetailsScreen
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_car_details.view.*

class CarDetailsFragment : Fragment() {
    private lateinit var viewModel: CarDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.supportFragmentManager
        return inflater.inflate(R.layout.fragment_car_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarDetailsViewModel::class.java)
        val carID = requireArguments().getInt("carID")
        viewModel.shownCar = SuperCarsDataLoader().getCarForID(carID)
        viewModel.carID = carID
        setViewPager()
    }

    private fun setViewPager() {
        val fragmentList: ArrayList<Fragment> = arrayListOf(
            CarDescriptionScreen(),
            CarGalleryScreen(),
            CarEquipmentScreen()
        )

        fragmentList.map {
            if (it is DetailsScreen) {
                it.setCarID(viewModel.carID)
            }
        }

        val adapter = DetailsViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        view?.detailsViewPager?.adapter = adapter
    }
}