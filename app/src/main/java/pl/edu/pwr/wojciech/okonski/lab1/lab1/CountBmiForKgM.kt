package pl.edu.pwr.wojciech.okonski.lab1.lab1

class CountBmiForKgM : ICountBmi {
    private val minimalMass = 10f
    private val maximalMass = 250f
    private val minimalHeight = 0.5f
    private val maximalHeight = 2.5f

    override fun isMassValid(mass: Float): Boolean {
        return mass > minimalMass && mass < maximalMass
    }

    override fun isHeightValid(height: Float): Boolean {
        return height > minimalHeight && height < maximalHeight
    }

    override fun calculateBMI(mass: Float, height: Float): Float {
        if (!isMassValid(mass) || !isHeightValid(height))
            throw IllegalArgumentException()
        return mass / (height * height)
    }
}