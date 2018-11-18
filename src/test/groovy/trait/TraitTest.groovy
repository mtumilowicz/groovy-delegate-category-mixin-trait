package trait

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-18.
 */
class TraitTest extends Specification {
    
    def "cormorant is flyer"() {
        expect:
        new Cormorant() instanceof Flyer
    }

    def "cormorant is swimmer"() {
        expect:
        new Cormorant() instanceof Swimmer
    }
    
    def "cormorant can swim"() {
        expect:
        new Cormorant().swim() == "swimming"
    }

    def "cormorant can fly"() {
        expect:
        new Cormorant().fly() == "flying"
    }

    def "penguin can swim"() {
        expect:
        new Penguin().swim() == "swimming"
    }

    def "penguin CAN'T fly"() {
        when:
        new Penguin().fly()
        
        then:
        thrown(MissingMethodException)
    }

    def "penguin is swimmer"() {
        expect:
        new Penguin() instanceof Swimmer
    }

    def "penguin is not flyer"() {
        expect:
        !(new Penguin() instanceof Flyer)
    }
}
