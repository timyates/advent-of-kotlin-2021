package com.bloidonia.advent.day03

import spock.lang.Specification

class Day3Spec extends Specification {

    def example = [
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010",
    ]

    def "Part 1"() {
        given:
        def input = example.collect {e ->
            use(Day03Kt) {
                e.toDiagnostic()
            }
        }

        when:
        def result = use(Day03Kt) {
            input.gammaTimesEpsilon()
        }

        then:
        result == 198
    }

    def "And part 2"() {
        given:
        def input = example.collect {e ->
            use(Day03Kt) {
                e.toDiagnostic()
            }
        }

        when:
        def result = use(Day03Kt) {
            input.part2()
        }

        then:
        result == 230
    }

}
