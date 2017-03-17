package pl.edu.pwr.wojciech.okonski.lab1.lab1

import spock.lang.Specification

class CountTest extends Specification {

    def "test"() {
        def testMass = -17f;
        def testHeight = 1.85f;

        when:
        def countBMITest = new CountBMIforKgM();
        countBMITest.calculateBMI(testMass, testHeight);

        then:
        thrown(IllegalArgumentException)
    }
}