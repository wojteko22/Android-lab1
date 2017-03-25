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
        setOnClickListener()
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(this, units, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setOnClickListener() {
        btnCount.setOnClickListener {
            hideSoftKeyboard(this)
            val massString = etMass.text.toString()
            val heightString = etHeight.text.toString()
            tryToCalculateBMI(massString, heightString)
        }
    }

    private fun tryToCalculateBMI(massString: String, heightString: String) {
        if (massString == "" || heightString == "")
            setInvalidInputPrompt()
        else
            tryToCalculateBMI(massString.toFloat(), heightString.toFloat())
    }

    private fun tryToCalculateBMI(mass: Float, height: Float) {
        try {
            val bmi = bmiCounter.calculateBMI(mass, height).toString()
            tvBmiResult.text = bmi
            setFireWorks(bmi.toFloat())
        } catch(e: IllegalArgumentException) {
            setInvalidInputPrompt()
        }
    }

    private fun setFireWorks(bmi: Float) {}

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    private fun setInvalidInputPrompt() {
        tvDescription.text = resources.getString(R.string.invalidInput)
    }
}