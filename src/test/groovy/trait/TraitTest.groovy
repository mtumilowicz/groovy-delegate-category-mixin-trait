package trait

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-18.
 */
class TraitTest extends Specification {
    
    def "cormorant can swim"() {
        expect:
        new Cormorant().swim() == "swim"
    }

    def "cormorant can fly"() {
        expect:
        new Cormorant().fly() == "flying"
    }

    def "penguin can swim"() {
        expect:
        new Penguin().swim() == "swim"
    }

    def "penguin CAN'T fly"() {
        when:
        new Penguin().fly()
        
        then:
        thrown(MissingMethodException)
    }
}
