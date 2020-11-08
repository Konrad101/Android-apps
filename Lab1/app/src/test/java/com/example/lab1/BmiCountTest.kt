package com.example.lab1

import com.example.lab1.bmi.BmiForCmKg
import com.example.lab1.bmi.BmiForInLbs
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class BmiCountTest : StringSpec({
    val accuracy = 0.001

    "testing bmi for american units"{
        BmiForInLbs(73.5, 162.8).count() shouldBe(21.185 plusOrMinus accuracy)
        BmiForInLbs(80.4, 190.0).count() shouldBe(20.663 plusOrMinus accuracy)
        BmiForInLbs(73.5, 0.0).count() shouldBe(0.0)
        BmiForInLbs(0.0, 162.8).count() shouldBe(0.0)
        BmiForInLbs(-73.5, 162.8).count() shouldBe(0.0)
        BmiForInLbs(73.5, -162.8).count() shouldBe(0.0)
        BmiForInLbs(-73.5, -162.8).count() shouldBe(0.0)
    }

    "testing bmi for units other than american"{
        BmiForCmKg(175.0, 64.0).count() shouldBe(20.897 plusOrMinus accuracy)
        BmiForCmKg(188.0, 107.5).count() shouldBe(30.415 plusOrMinus accuracy)
        BmiForCmKg(176.0, 0.0).count() shouldBe(0.0)
        BmiForCmKg(0.0, 75.0).count() shouldBe(0.0)
        BmiForCmKg(-175.0, 64.0).count() shouldBe(0.0)
        BmiForCmKg(175.0, -64.0).count() shouldBe(0.0)
        BmiForCmKg(-175.0, -64.0).count() shouldBe(0.0)
    }
})
