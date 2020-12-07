package and.lab.katalog.carsDetails.screens.equipment

import and.lab.katalog.R
import and.lab.katalog.carsContent.SuperCarsDataLoader
import and.lab.katalog.carsDetails.CarDetailsViewModel
import and.lab.katalog.carsDetails.screens.DetailsScreen
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_equipment_screen.*

class CarEquipmentScreen : Fragment(), DetailsScreen {
    private lateinit var viewModel: CarDetailsViewModel
    private lateinit var screenInflater: LayoutInflater
    private var carID: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CarDetailsViewModel::class.java)
        if (carID != -1) {
            viewModel.carID = carID
        }
        screenInflater = inflater
        return inflater.inflate(R.layout.fragment_equipment_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEquipment(screenInflater)
    }

    private fun setEquipment(inflater: LayoutInflater) {
        if (viewModel.equipmentList.isEmpty()) {
            viewModel.equipmentList = SuperCarsDataLoader().getCarEquipment(viewModel.carID)
        }

        val adapter = GridViewAdapter(this.requireContext(), inflater, viewModel.equipmentList)
        equipmentGridView.adapter = adapter
    }

    override fun setCarID(id: Int) {
        carID = id
    }
}