package and.lab.katalog.carsDetails

import and.lab.katalog.carsContent.SuperCar
import androidx.lifecycle.ViewModel

class CarDetailsViewModel : ViewModel() {
    var carID = 0
    lateinit var shownCar: SuperCar
    var photosList: List<Int> = listOf()
    var equipmentList: List<String> = listOf()
}