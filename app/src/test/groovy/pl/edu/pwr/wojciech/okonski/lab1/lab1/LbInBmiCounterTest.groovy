package pl.edu.pwr.wojciech.okonski.lab1.lab1

import spock.lang.Specification
import spock.lang.Unroll

import static spock.util.matcher.HamcrestMatchers.closeTo

class LbInBmiCounterTest extends Specification {
    def countBmi = new LbInBmiCounter()

    @Unroll
    def "verify mass #mass"() {
        when:
        def isValid = countBmi.isMassValid(mass)

        then:
        isValid == expectedResult

        where:
        mass   || expectedResult
        21.9f  || false
        22f    || true
        550f   || true
        550.1f || false
    }

    @Unroll
    def "verify height #height"() {
        when:
        def isValid = countBmi.isHeightValid(height)

        then:
        isValid == expectedResult

        where:
        height || expectedResult
        19.9f  || false
        20f    || true
        100f   || true
        100.1f || false
    }

    @Unroll
    def "throw exception for mass #mass and height #height"() {
        when:
        countBmi.calculateBMI(mass, height)

        then:
        thrown(IllegalArgumentException)

        where:
        mass   | height
        21.9f  | 70f
        550.1f | 70f
        150f   | 19.9f
        150f   | 100.1f
    }

    def "count BMI"() {
        when:
        def actual = countBmi.calculateBMI(mass, height)

        then:
        actual closeTo(expectedResult, acceptableError)

        where:
        mass = 148.81f
        height = 72.83f
        expectedResult = 19.7f
        acceptableError = 0.1f
    }
}
