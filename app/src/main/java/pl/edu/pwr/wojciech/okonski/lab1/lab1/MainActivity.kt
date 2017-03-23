package pl.edu.pwr.wojciech.okonski.lab1.lab1

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import pl.edu.pwr.wojciech.okonski.lab1.lab1.R.array.units


class MainActivity : AppCompatActivity() {
    private val bmiCounter = CountBmiForKgM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSpinner()
        setMassChecker()
        setOnClickListener()
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(this, units, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setMassChecker() {
        etMass.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val mass = etMass.text.toString().toFloat()
                if (!bmiCounter.isMassValid(mass))
                    tvDescription.text = resources.getString(R.string.wrongMass)
            }
        }
    }

    private fun setOnClickListener() {
        btnCount.setOnClickListener {
            hideSoftKeyboard(this)
            val mass = etMass.text.toString().toFloat()
            val height = etHeight.text.toString().toFloat()
            tryToCalculateBMI(mass, height)
        }
    }

    private fun tryToCalculateBMI(mass: Float, height: Float) {
        try {
            val bmi = bmiCounter.calculateBMI(mass, height).toString()
            tvBmiResult.text = bmi
        } catch(e: IllegalArgumentException) {
            tvDescription.text = resources.getString(R.string.wrongInput)
        }
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }
}