package pl.edu.pwr.wojciech.okonski.lab1.lab1

import org.junit.Test
import spock.lang.Specification

public class ATest extends Specification {

    @Test
    public void test() {
        when:
        def a = 3 + 4

        then:
        a == 7
    }

}