package com.bloidonia.advent.day06

import spock.lang.Specification

class Day6Spec extends Specification {

    def example = '3,4,3,1,2'

    def "example for part 1"() {
        when:
        def initial = use(Day06Kt) {
            example.toPopulation()
        }

        def day18 = (1..18).inject(initial) { acc, _ -> acc.generation() }

        then:
        day18.size() == 26

        when:
        def day80 = (1..80).inject(initial) { acc, _ -> acc.generation() }

        then:
        day80.size() == 5934
    }

    def "part 2"() {
        when:
        def initial = use(Day06Kt) {
            example.toPopulation()
        }

        def day256 = (1..256).inject(initial) { acc, _ -> acc.generation() }

        then:
        day256.size() == 26984457539
    }
}
