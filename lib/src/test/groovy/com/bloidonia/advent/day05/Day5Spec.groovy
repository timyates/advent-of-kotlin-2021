package com.bloidonia.advent.day05

import spock.lang.Specification

import static com.bloidonia.advent.day05.Day05Kt.coveragePart1
import static com.bloidonia.advent.day05.Day05Kt.coveragePart2

class Day5Spec extends Specification {

    def example = [
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2",
    ]

    def "can parse vents"() {
        given:
        def vent = use(Day05Kt) { example[0].toVent() }

        println vent

        expect:
        vent.start.x == 0
        vent.start.y == 9
        vent.end.x == 5
        vent.end.y == 9
    }

    def "can detect diagonals"() {
        when:
        def vent = use(Day05Kt) { '0,9 -> 5,9'.toVent() }

        then:
        vent.notDiagonal()

        when:
        vent = use(Day05Kt) { '8,0 -> 0,8'.toVent() }

        then:
        !vent.notDiagonal()
    }

    def "can calculate covered points"() {
        when:
        def points = use(Day05Kt) { '7,4 -> 7,0'.toVent() }.coveredPoints()

        then:
        points*.toString() == ['(7,4)', '(7,3)', '(7,2)', '(7,1)', '(7,0)']

        when:
        points = use(Day05Kt) { '0,9 -> 5,9'.toVent() }.coveredPoints()

        then:
        points*.toString() == ['(0,9)', '(1,9)', '(2,9)', '(3,9)', '(4,9)', '(5,9)']
    }

    def "can count coverage (horiz and vert)"() {
        when:
        def vents = use(Day05Kt) {
            example.collect {
                it.toVent()
            }
        }

        then:
        coveragePart1(vents) == 5
    }

    def "can calculate diagonals"() {
        when:
        def points = use(Day05Kt) { '7,4 -> 3,0'.toVent() }.coveredPoints()

        then:
        points*.toString() == ['(7,4)', '(6,3)', '(5,2)', '(4,1)', '(3,0)']
    }

    def "can count coverage (diagonals too)"() {
        when:
        def vents = use(Day05Kt) {
            example.collect {
                it.toVent()
            }
        }

        then:
        coveragePart2(vents) == 12
    }
}
