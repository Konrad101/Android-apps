package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.bmi.Bmi
import com.example.lab1.bmi.BmiForCmKg
import com.example.lab1.bmi.BmiForInLbs
import com.example.lab1.dataHistory.HistoryActivity
import com.example.lab1.databinding.ActivityMainBinding
import com.example.lab1.bmiDecorators.BmiTVDecorator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var americanUnits: Boolean = false

    private lateinit var sharedPrefHistory: SharedPreferences
    private val historyPrefKey = "History"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPrefHistory = getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.kg_cm -> {
                americanUnits = false
                updateTextViewsUnits()
                true
            }
            R.id.lbs_in -> {
                americanUnits = true
                updateTextViewsUnits()
                true
            }
            R.id.history -> {
                startHistoryActivity()
                true
            }
            R.id.clr_history -> {
                clearHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        val historyList = getHistoryListFromPreferences()
        intent.putExtra("history_list", historyList)
        startActivityForResult(intent, 0)
    }

    private fun clearHistory(){
        val bmiHistory: ArrayList<BmiDataHolder> = arrayListOf()
        sharedPrefHistory.edit().apply {
            val gsonHistory: String = Gson().toJson(bmiHistory)
            putString(historyPrefKey, gsonHistory)
            apply()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("BMI_result", bmiTV.text.toString())
        outState.putBoolean("units", americanUnits)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        bmiTV.text = savedInstanceState.getString("BMI_result")

        val bmiValue = bmiTV.text.toString().toDouble()
        BmiTVDecorator(bmiTV, bmiValue).colorBmiValue()

        americanUnits = savedInstanceState.getBoolean("units")
        if (americanUnits) {
            updateTextViewsUnits()
        }
    }

    private fun getHistoryListFromPreferences(): ArrayList<BmiDataHolder> {
        val historyJson = sharedPrefHistory.getString(historyPrefKey, "")
        val listType = object : TypeToken<List<BmiDataHolder>>() {}.type
        return Gson().fromJson(historyJson, listType)
    }

    private fun saveBmiDataInHistory(bmi: Double, mass: Double, height: Double) {
        val bmiHistory: ArrayList<BmiDataHolder> = getHistoryListFromPreferences()
        bmiHistory.add(
            BmiDataHolder(
                bmi = bmi,
                weight = mass,
                height = height,
                americanUnits = americanUnits,
                calculationDate = Date()
            )
        )
        sharedPrefHistory.edit().apply {
            val gsonHistory: String = Gson().toJson(bmiHistory)
            putString(historyPrefKey, gsonHistory)
            apply()
        }
    }

    private fun updateTextViewsUnits() {
        if (americanUnits) {
            massTV.text = getString(R.string.mass_lbs)
            heightTV.text = getString(R.string.height_in)
        } else {
            massTV.text = getString(R.string.mass_kg)
            heightTV.text = getString(R.string.height_cm)
        }
    }

    fun count(view: View) {
        val lowestCorrectHeightCm = 80
        val highestCorrectHeightCm = 250
        val lowestCorrectHeightIn = lowestCorrectHeightCm * 0.3937
        val highestCorrectHeightIn = highestCorrectHeightCm * 0.3937

        val lowestCorrectWeightKg = 15
        val lowestCorrectWeightLbs = lowestCorrectWeightKg * 0.2046

        binding.apply {
            var dataIsCorrect = true
            if (heightET.text.isBlank()) {
                heightET.error = getString(R.string.height_is_empty)
                dataIsCorrect = false
            }
            if (massET.text.isBlank()) {
                massET.error = getString(R.string.mass_is_empty)
                dataIsCorrect = false
            }

            if (dataIsCorrect) {
                val height: Double = heightET.text.toString().toDouble()
                val mass: Double = massET.text.toString().toDouble()
                if (!americanUnits && height < lowestCorrectHeightCm || americanUnits && height < lowestCorrectHeightIn) {
                    heightET.error = getString(R.string.height_is_too_small)
                    dataIsCorrect = false
                } else if (!americanUnits && height > highestCorrectHeightCm || americanUnits && height > highestCorrectHeightIn) {
                    heightET.error = getString(R.string.height_is_too_big)
                    dataIsCorrect = false
                } else if (!americanUnits && mass < lowestCorrectWeightKg || americanUnits && mass < lowestCorrectWeightLbs) {
                    massET.error = getString(R.string.weight_is_too_small)
                    dataIsCorrect = false
                }

                if (dataIsCorrect) {
                    val bmiCalculator: Bmi = if (americanUnits) {
                        BmiForInLbs(height, mass)
                    } else {
                        BmiForCmKg(height, mass)
                    }
                    val bmi: Double = bmiCalculator.count()
                    val bmiText = String.format("%.2f", bmi)
                    if(bmiText != bmiTV.text.toString()) {
                        bmiTV.text = bmiText
                        saveBmiDataInHistory(bmi, mass, height)
                        val bmiDecorator = BmiTVDecorator(bmiTV, bmi)
                        bmiDecorator.colorBmiValue()
                    }
                }
            }
        }
    }

    fun showBmiDetails(view: View) {
        val bmi: Double = bmiTV.text.toString().replace(",", ".").toDouble()
        if (bmi > 0) {
            val intent = Intent(this, BmiResultActivity::class.java)
            intent.putExtra("bmiResult", bmi)
            startActivityForResult(intent, 0)
        }
    }
}