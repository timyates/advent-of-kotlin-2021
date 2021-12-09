package com.bloidonia.advent.day09

import spock.lang.Specification

class Day9Spec extends Specification {

    def example = [
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678",
    ]

    def "Part 1 example"() {
        given:
        def result = use(Day09Kt) {
            example.toCave()
        }

        expect:
        result.part1() == 15
    }

    def "basin test"() {
        given:
        def result = use(Day09Kt) {
            example.toCave()
        }

        expect:
        result.basin(new Point(1, 0)) == 3
        result.basin(new Point(9, 0)) == 9
        result.basin(new Point(2, 2)) == 14
        result.basin(new Point(6, 4)) == 9
    }

    def "part 2 top 3 result"() {
        given:
        def result = use(Day09Kt) {
            example.toCave()
        }

        expect:
        result.part2() == 1134
    }
}
