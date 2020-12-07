package and.lab.katalog.carsContent

import and.lab.katalog.R
import and.lab.katalog.carsList.CarListItem

class SuperCarsDataLoader {
    private val maxCarID = 8

    fun getSuperCarsList(): MutableList<CarListItem> {
        val superCars = mutableListOf<CarListItem>()
        for (id in 1..maxCarID) {
            superCars.add(
                CarListItem(
                    id,
                    getCarForID(id),
                    false
                )
            )
        }

        return superCars
    }

    fun getCarForID(id: Int): SuperCar {
        return when (id) {
            1 -> SuperCar(
                5.2,
                "V10",
                "Lamborghini",
                "Huracan Performante",
                273000.0,
                639
            )
            2 -> SuperCar(
                6.5,
                "V12",
                "Lamborghini",
                "Murcielago SV",
                450000.0,
                670
            )
            3 -> SuperCar(
                6.5,
                "V12",
                "Ferrari",
                "812 Superfast",
                337000.0,
                789
            )
            4 -> SuperCar(
                3.9,
                "V8",
                "Ferrari",
                "488 Pista",
                330000.0,
                710
            )
            5 -> SuperCar(
                4.0,
                "V8",
                "McLaren",
                "765lt",
                358000.0,
                755
            )
            6 -> SuperCar(
                4.6,
                "V8",
                "Porsche",
                "918",
                845000.0,
                875
            )
            7 -> SuperCar(
                5.2,
                "V12",
                "Aston Martin",
                "DBS Superleggera",
                305000.0,
                715
            )
            else -> SuperCar(
                6.0,
                "V12",
                "Maserati",
                "MC12",
                770000.0,
                630
            )
        }
    }

    fun getCarImageForID(id: Int): Int {
        return when (id) {
            2 -> R.mipmap.ic_lamborghini_murcielago_sv
            3 -> R.mipmap.ic_ferrari_812_superfast
            4 -> R.mipmap.ic_ferrari_488_pista
            5 -> R.mipmap.ic_mclaren_765lt
            6 -> R.mipmap.ic_porsche_918
            7 -> R.mipmap.ic_aston_martin_dbs
            8 -> R.mipmap.ic_maserati_mc12
            else -> R.mipmap.ic_lamborghini_huracan_p
        }
    }

    fun getCarGallery(id: Int): List<Int> {
        val photosAmount = 3
        val gallery = arrayListOf<Int>()
        var carID = id
        gallery.add(getCarImageForID(carID))

        for (i in 1 until photosAmount) {
            carID = (carID + (1 until maxCarID).random()) % (maxCarID + 1)
            if (carID == id || carID == 0) {
                carID = (carID + 1) % (maxCarID + 1)
            }
            gallery.add(getCarImageForID(carID))
        }

        return gallery
    }

    fun getCarDescription(id: Int): String {
        val car: SuperCar = getCarForID(id)
        val description: StringBuilder = StringBuilder(
            car.brand + " " + car.model + " has reworked the concept of super " +
                    "sports cars and taken the notion of performance to levels never seen before. " +
                    "The vehicle has been re-engineered in its entirety, as regards its weight, engine " +
                    "power, chassis and above all by introducing an innovative system of active aerodynamics." +
                    " Besides its extraordinary technological properties, it also conveys a new idea of beauty."
        )

        if (car.model.contains("Murcielago")) {
            description.append(" It is one of the best sounding cars on the world.")
        } else if (car.model.contains("MC12")) {
            description.append(" It is one of the most iconic supercars of the 21st century.")
        }

        return description.toString()
    }

    fun getCarEquipment(id: Int): List<String> {
        val equipmentList: MutableList<String> = mutableListOf()
        val car = getCarForID(id)
        val standardEquipment: List<String> = listOf(
            "Cup holder", "Alcantara interior", "Magnesium wheels", "Carbon bucket seats"
        )
        val brakes: List<String> = listOf("Steel brakes", "Carbon ceramic brakes")
        val extraEquipment: List<String> = listOf(
            "Titanium exhaust", "Carbon car body", "Aerodynamic package"
        )

        equipmentList.addAll(standardEquipment)
        if (car.model.contains("Murcielago")) {
            equipmentList.add(brakes[(brakes.indices).random()])
        } else {
            equipmentList.add(brakes[0])
        }
        for (equipment in extraEquipment) {
            if ((0..2).random() % 2 == 0) {
                equipmentList.add(equipment)
            }
        }

        return equipmentList
    }
}