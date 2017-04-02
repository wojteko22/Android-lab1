package pl.edu.pwr.wojciech.okonski.lab1.lab1

interface BmiCounter {
    val minimalMass: Float
    val maximalMass: Float
    val minimalHeight: Float
    val maximalHeight: Float
    fun isMassValid(mass: Float) = mass in minimalMass..maximalMass
    fun isHeightValid(height: Float) = height in minimalHeight..maximalHeight
    fun calculateBMI(mass: Float, height: Float): Float
}