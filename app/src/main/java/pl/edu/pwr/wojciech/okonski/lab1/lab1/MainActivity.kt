package pl.edu.pwr.wojciech.okonski.lab1.lab1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val bmiCounter = CountBmiForKgM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        btnCount.setOnClickListener {
            Log.e("Mass", etMass.text.toString())
            val mass = etMass.text.toString().toFloat()
            val height = etHeight.text.toString().toFloat()
            val bmi = bmiCounter.calculateBMI(mass, height).toString()
            tvBmiResult.text = bmi
        }
    }
}