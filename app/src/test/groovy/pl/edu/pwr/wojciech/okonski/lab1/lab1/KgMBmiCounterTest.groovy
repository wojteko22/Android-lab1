package pl.edu.pwr.wojciech.okonski.lab1.lab1

import spock.lang.Specification
import spock.lang.Unroll

import static spock.util.matcher.HamcrestMatchers.closeTo

class KgMBmiCounterTest extends Specification {
    def countBmi = new KgMBmiCounter();

    @Unroll
    def "verify mass #mass"() {
        when:
        def isValid = countBmi.isMassValid(mass)

        then:
        isValid == expectedResult

        where:
        mass   || expectedResult
        9.9f   || false
        10f    || true
        250f   || true
        250.1f || false
    }

    @Unroll
    def "verify height #height"() {
        when:
        def isValid = countBmi.isHeightValid(height)

        then:
        isValid == expectedResult

        where:
        height || expectedResult
        0.49f  || false
        0.5f   || true
        2.5f   || true
        2.51f  || false
    }

    @Unroll
    def "throw exception for mass #mass and height #height"() {
        when:
        countBmi.calculateBMI(mass, height);

        then:
        thrown(IllegalArgumentException)

        where:
        mass   | height
        9.9f   | 1.5f
        250.1f | 1.5f
        80f    | 0.49f
        80f    | 2.51f
    }

    def "count BMI"() {
        when:
        def actual = countBmi.calculateBMI(mass, height)

        then:
        actual closeTo(expectedResult, acceptableError)

        where:
        mass = 67.5f
        height = 1.85f
        expectedResult = 19.7f
        acceptableError = 0.1f
    }
}