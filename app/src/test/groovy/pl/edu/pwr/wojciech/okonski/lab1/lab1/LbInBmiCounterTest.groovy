package pl.edu.pwr.wojciech.okonski.lab1.lab1

import spock.lang.Specification
import spock.lang.Unroll

import static spock.util.matcher.HamcrestMatchers.closeTo

class LbInBmiCounterTest extends Specification {
    def countBmi = new LbInBmiCounter();

    @Unroll
    def "verify mass #mass"() {
        when:
        def isValid = countBmi.isMassValid(mass)

        then:
        isValid == expectedResult

        where:
        mass     || expectedResult
        22.05f   || false
        22.051f  || true
        551.159f || true
        551.16f  || false
    }

    @Unroll
    def "verify height #height"() {
        when:
        def isValid = countBmi.isHeightValid(height)

        then:
        isValid == expectedResult

        where:
        height  || expectedResult
        19.69f  || false
        19.691f || true
        98.429f || true
        98.43f  || false
    }

    @Unroll
    def "throw exception for mass #mass and height #height"() {
        when:
        countBmi.calculateBMI(mass, height);

        then:
        thrown(IllegalArgumentException)

        where:
        mass    | height
        22.05f  | 70f
        551.16f | 70f
        150f    | 19.69f
        150f    | 98.43f
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
