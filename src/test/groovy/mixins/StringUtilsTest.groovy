package mixins

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-18.
 */
class StringUtilsTest extends Specification {
    
    def setupSpec() {
        String.mixin StringUtils
    }

    def "string concat with comma"() {
        expect:
        "a".concatWithComma("b") == "a,b"
    }
}
