package pl.edu.pwr.wojciech.okonski.lab1.lab1

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import pl.edu.pwr.wojciech.okonski.lab1.lab1.R.array.units
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var bmiCounter: BmiCounter by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSpinner()
        btnCount.setOnClickListener { displayBmiStuff() }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(this, units, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun displayBmiStuff() {
        hideSoftKeyboard(this)
        val massString = etMass.text.toString()
        val heightString = etHeight.text.toString()
        tryToCalculateBMI(massString, heightString)
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    private fun tryToCalculateBMI(massString: String, heightString: String) {
        try {
            tryToCalculateBMI(massString.toFloat(), heightString.toFloat())
        } catch (e: NumberFormatException) {
            handleInvalidInput()
        }
    }

    private fun tryToCalculateBMI(mass: Float, height: Float) {
        setBmiCounter()
        try {
            calculateBmi(mass, height)
        } catch(e: IllegalArgumentException) {
            handleInvalidInput()
        }
    }

    private fun setBmiCounter() {
        bmiCounter =
                if (isKgMSelected())
                    KgMBmiCounter()
                else
                    LbInBmiCounter()
    }

    private fun isKgMSelected() = spinner.selectedItemPosition == 0

    private fun calculateBmi(mass: Float, height: Float) {
        val bmi = bmiCounter.calculateBMI(mass, height).toString()
        tvBmiResult.text = bmi
        setFireWorks(bmi.toFloat())
    }

    private fun setFireWorks(bmi: Float) {
        when (bmi) {
            in 30f..Float.POSITIVE_INFINITY -> {
                setDescription(R.string.obese)
                setColor(Color.RED)
            }
            in 25..30 -> {
                setDescription(R.string.overweight)
                setColor(Color.MAGENTA)
            }
            in 18.5f..25f -> {
                setDescription(R.string.normalBmi)
                setColor(Color.GREEN)
            }
            in 16.5f..18.5f -> {
                setDescription(R.string.underweight)
                setColor(Color.MAGENTA)
            }
            else -> {
                setDescription(R.string.seriouslyUnderweight)
                setColor(Color.RED)
            }
        }
    }

    private fun setDescription(stringId: Int) {
        tvDescription.text = resources.getString(stringId)
    }

    private fun setColor(color: Int) {
        tvBmiResult.setTextColor(color)
    }

    private fun handleInvalidInput() {
        cleanTextIn(tvBmiResult)
        setDescription(R.string.invalidInput)
    }

    private fun cleanTextIn(textView: TextView) {
        textView.text = ""
    }
}