package pl.edu.pwr.wojciech.okonski.lab1.lab1

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class CountBMITest {
    @Test
    fun massUnderZeroIsInvalid() {
        // GIVEN
        val testMass = -1.0f
        val countBMITest = CountBmiForKgM()

        // WHEN
        val actual = countBMITest.isMassValid(testMass)

        // THEN
        assertFalse(actual)
    }

    @Test
    fun bmiValid() {
        // GIVEN
        val testMass = 67f
        val testHeight = 1.85f
        val countBMITest = CountBmiForKgM()

        // WHEN
        val actual = countBMITest.calculateBMI(testMass, testHeight)

        // THEN
        assertEquals(19.5, actual.toDouble(), 0.1)
    }
}
