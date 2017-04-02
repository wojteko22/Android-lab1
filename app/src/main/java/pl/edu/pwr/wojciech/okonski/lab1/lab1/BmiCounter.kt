package pl.edu.pwr.wojciech.okonski.lab1.lab1

interface BmiCounter {
    val minimalMass: Float
    val maximalMass: Float
    val minimalHeight: Float
    val maximalHeight: Float
    fun isMassValid(mass: Float): Boolean
    fun isHeightValid(height: Float): Boolean
    fun calculateBMI(mass: Float, height: Float): Float
}