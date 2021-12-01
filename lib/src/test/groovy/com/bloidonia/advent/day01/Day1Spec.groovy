package com.bloidonia.advent.day01

import spock.lang.Specification

class Day1Spec extends Specification {

    def example = [
            199,
            200,
            208,
            210,
            200,
            207,
            240,
            269,
            260,
            263,
    ]

    def "can count increases"() {
        given:
        def day = new Day01()

        when:
        def result = day.countIncrements(example)

        then:
        result == 7
    }

    def "can sum windows"() {
        given:
        def day = new Day01()

        when:
        def result = day.windowSums(example)

        then:
        result == [607, 618, 618, 617, 647, 716, 769, 792]
    }
}
