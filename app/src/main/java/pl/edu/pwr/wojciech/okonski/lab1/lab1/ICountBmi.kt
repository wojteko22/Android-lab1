package pl.edu.pwr.wojciech.okonski.lab1.lab1

interface ICountBmi {
    fun isMassValid(mass: Float): Boolean
    fun isHeightValid(height: Float): Boolean
    fun calculateBMI(mass: Float, height: Float): Float
}