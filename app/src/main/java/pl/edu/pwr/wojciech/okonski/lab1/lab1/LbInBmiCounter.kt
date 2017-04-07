package pl.edu.pwr.wojciech.okonski.lab1.lab1

class LbInBmiCounter : BmiCounter {
    override val minimalMass = 22f
    override val maximalMass = 550f
    override val minimalHeight = 20f
    override val maximalHeight = 100f

    override fun calculateBMI(mass: Float, height: Float): Float {
        super.calculateBMI(mass, height)
        val bmi = mass / (height * height) * 703
        return Math.round(bmi * 100f) / 100f
    }
}