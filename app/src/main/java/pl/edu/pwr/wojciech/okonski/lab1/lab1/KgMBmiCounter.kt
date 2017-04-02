package pl.edu.pwr.wojciech.okonski.lab1.lab1

class KgMBmiCounter : BmiCounter {
    override val minimalMass = 10f
    override val maximalMass = 250f
    override val minimalHeight = 0.5f
    override val maximalHeight = 2.5f

    override fun calculateBMI(mass: Float, height: Float): Float {
        if (!isMassValid(mass) || !isHeightValid(height))
            throw IllegalArgumentException()
        val bmi = mass / (height * height)
        return Math.round(bmi * 100f) / 100f
    }
}