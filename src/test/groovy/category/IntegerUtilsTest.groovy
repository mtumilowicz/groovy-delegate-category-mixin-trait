package category

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-18.
 */
class IntegerUtilsTest extends Specification {
    def "even"() {
        expect:
        use(IntegerUtils) {
            2.even()
        }
    }

    def "not even"() {
        expect:
        use(IntegerUtils) {
            !1.even()
        }
    }
}
