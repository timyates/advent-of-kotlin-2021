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

    def "can parse"() {
        when:
        def temp = use(Day14Kt) { example.parsePolymers() }

        then:
        temp.polymer == ['N', 'N', 'C', 'B']
        temp.insertions.size() == 16

        temp.insertionFor(['B', 'H']) == ['H']
        temp.insertionFor(['N', 'O']) == []
    }

    def "step for part 1"() {
        when:
        def temp = use(Day14Kt) { example.parsePolymers() }
        temp = temp.step()

        then:
        temp.polymer.join("") == "NCNBCHB"

        when:
        temp = temp.step()

        then:
        temp.polymer.join("") == "NBCCNBBBCBHCB"

        when:
        temp = temp.step()

        then:
        temp.polymer.join("") == "NBBBCNCCNBBNBNBBCHBHHBCHB"

        when:
        temp = temp.step()

        then:
        temp.polymer.join("") == "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"
    }

    def "part 1"() {
        when:
        def temp = use(Day14Kt) { example.parsePolymers() }
        for (i in 1..10) {
            temp = temp.step()
        }

        then:
        temp.part1() == 1588
    }

}
