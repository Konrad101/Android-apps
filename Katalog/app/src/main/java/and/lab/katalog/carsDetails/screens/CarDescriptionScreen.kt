package and.lab.katalog.carsDetails.screens

import and.lab.katalog.R
import and.lab.katalog.carsContent.SuperCarsDataLoader
import and.lab.katalog.carsDetails.CarDetailsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_description_screen.*
import kotlin.math.roundToInt


class CarDescriptionScreen : Fragment(), DetailsScreen {
    private lateinit var viewModel: CarDetailsViewModel
    private var carID: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CarDetailsViewModel::class.java)
        if (carID != -1) {
            viewModel.carID = carID
        }
        return inflater.inflate(R.layout.fragment_description_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextViews()
        backButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_car_details_to_category_list)
        }
    }

    private fun setTextViews() {
        if (textViewsNotInitialized()) {
            val carDataLoader = SuperCarsDataLoader()
            val superCar = carDataLoader.getCarForID(viewModel.carID)

            brandNameTV.text = superCar.brand
            carModelTV.text = superCar.model
            descriptionTV.text = carDataLoader.getCarDescription(viewModel.carID)
            val priceText: String = priceTV.text as String + getPrintPriceFormat(superCar.price)
            priceTV.text = priceText
        }
    }

    private fun getPrintPriceFormat(price: Double): String {
        val priceAsString: String = price.roundToInt().toString()
        val printFormat: StringBuilder = StringBuilder()
        var digits = 0
        val lastPriceIndex = priceAsString.length - 1
        for (i in (priceAsString.indices)) {
            printFormat.append(priceAsString[lastPriceIndex - i])
            digits += 1
            if (digits % 3 == 0 && i != lastPriceIndex) {
                printFormat.append(",")
            }
        }
        printFormat.reverse()
        return " $$printFormat"
    }

    private fun textViewsNotInitialized(): Boolean {
        return brandNameTV.text.isEmpty() && carModelTV.text.isEmpty()
                && descriptionTV.text.isEmpty()
    }

    override fun setCarID(id: Int) {
        carID = id
    }
}