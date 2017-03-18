package pl.edu.pwr.wojciech.okonski.lab1.lab1

import spock.lang.Specification
import spock.lang.Unroll

import static spock.util.matcher.HamcrestMatchers.closeTo

class CountBmiForKgMTest extends Specification {
    def countBmi = new CountBmiForKgM();

    @Unroll
    def "verify mass #mass"() {
        when:
        def isValid = countBmi.isMassValid(mass)

        then:
        isValid == expectedResult

        where:
        mass   || expectedResult
        10f    || false
        10.1f  || true
        249.9f || true
        250f   || false
    }

    @Unroll
    def "verify height #height"() {
        when:
        def isValid = countBmi.isHeightValid(height)

        then:
        isValid == expectedResult

        where:
        height || expectedResult
        0.5f   || false
        0.51f  || true
        2.49f  || true
        2.5f   || false
    }

    @Unroll
    def "throw exception for mass #mass and height #height"() {
        when:
        countBmi.calculateBMI(mass, height);

        then:
        thrown(IllegalArgumentException)

        where:
        mass | height
        10f  | 1.5f
        250f | 1.5f
        80f  | 0.5f
        80f  | 2.5f
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