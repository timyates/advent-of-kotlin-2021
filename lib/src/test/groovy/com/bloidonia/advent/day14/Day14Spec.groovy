package com.bloidonia.advent.day14

import spock.lang.Ignore
import spock.lang.Specification

class Day14Spec extends Specification {

    def example = '''NNCB
                    |
                    |CH -> B
                    |HH -> N
                    |CB -> H
                    |NH -> C
                    |HB -> C
                    |HC -> B
                    |HN -> C
                    |NN -> C
                    |BH -> H
                    |NC -> B
                    |NB -> B
                    |BN -> B
                    |BB -> N
                    |BC -> B
                    |CC -> N
                    |CN -> C'''.stripMargin()

    def "part 1"() {
        when:
        def temp = use(Day14Kt) { example.parsePolymers() }
        for (i in 1..10) {
            temp = temp.step()
        }

        then:
        temp.count() == 1588
    }

    def "part 2"() {
        when:
        def temp = use(Day14Kt) { example.parsePolymers() }
        for (i in 1..40) {
            temp = temp.step()
        }

        then:
        temp.count() == 2188189693529L
    }

}
