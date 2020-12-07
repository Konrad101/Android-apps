package and.lab.katalog.carsList

import and.lab.katalog.carsContent.SuperCarsDataLoader
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SuperCarsListViewModel : ViewModel() {
    private val allSuperCars: MutableList<CarListItem> = SuperCarsDataLoader().getSuperCarsList()
    private val shownSuperCars: MutableLiveData<MutableList<CarListItem>>? = MutableLiveData()
    private var onlyFavorite: Boolean = false

    fun getCarsList(): MutableList<CarListItem> {
        return allSuperCars.toMutableList()
    }

    fun getShownCarsLiveData(): LiveData<MutableList<CarListItem>>? {
        return shownSuperCars
    }

    fun removeCar(carItem: CarListItem) {
        allSuperCars.remove(carItem)
        shownSuperCars?.value?.remove(carItem)
        shownSuperCars?.value = shownSuperCars?.value?.toMutableList()
    }

    fun setShownSuperCars(shownCars: MutableList<CarListItem>) {
        shownSuperCars?.value = shownCars
    }

    fun getShownSuperCars(): MutableList<CarListItem>? {
        return shownSuperCars?.value?.toMutableList()
    }

    fun onlyFavoriteCars(): Boolean {
        return onlyFavorite
    }

    fun setOnlyFavorite(onlyFav: Boolean) {
        onlyFavorite = onlyFav
    }
}