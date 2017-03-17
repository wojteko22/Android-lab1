package pl.edu.pwr.wojciech.okonski.lab1.lab1

import spock.lang.Specification

class CountBmiForKgMTest extends Specification {
    def "IsMassValid"() {

    }

    def "IsHeightValid"() {

    }

    def "CalculateBMI"() {

    }

    def "test"() {
        def testMass = -17f;
        def testHeight = 1.85f;

        when:
        def countBMITest = new CountBmiForKgM();
        countBMITest.calculateBMI(testMass, testHeight);

        then:
        thrown(IllegalArgumentException)
    }
}
