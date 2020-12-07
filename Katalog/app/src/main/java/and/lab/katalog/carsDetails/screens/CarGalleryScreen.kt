package and.lab.katalog.carsDetails.screens

import and.lab.katalog.R
import and.lab.katalog.carsContent.SuperCarsDataLoader
import and.lab.katalog.carsDetails.CarDetailsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_gallery_screen.*


class CarGalleryScreen : Fragment(), DetailsScreen {
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
        return inflater.inflate(R.layout.fragment_gallery_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.photosList.isEmpty()) {
            viewModel.photosList = SuperCarsDataLoader().getCarGallery(viewModel.carID)
        }
        val carGallery: List<Int> = viewModel.photosList

        var childIndex = 0
        var photoIndex = 0
        while (childIndex < galleryLayout.childCount && photoIndex < carGallery.size) {
            val v: View = galleryLayout.getChildAt(childIndex)
            if (v is ImageView) {
                v.setImageResource(carGallery[photoIndex++])
            }
            childIndex++
        }
    }

    override fun setCarID(id: Int) {
        carID = id
    }
}