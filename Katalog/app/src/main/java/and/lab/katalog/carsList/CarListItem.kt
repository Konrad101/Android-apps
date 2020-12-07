package and.lab.katalog.carsList

import and.lab.katalog.carsContent.SuperCar

class CarListItem(
    val id: Int,
    val car: SuperCar,
    var isFavorite: Boolean = false
)