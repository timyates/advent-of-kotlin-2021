package com.bloidonia.advent

import spock.lang.Specification

class LibrarySpec extends Specification {

    def "spock works"() {
        expect:
        new Library().someLibraryMethod()
    }
}
