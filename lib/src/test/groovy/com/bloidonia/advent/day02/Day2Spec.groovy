package com.bloidonia.advent.day02

import spock.lang.Specification

class Day2Spec extends Specification {

    def example = [
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2",
    ]

    def "part 1 example works"() {
        given:
        def day = new Day02()

        when:
        def input = use(Day02Kt) {
            example.collect { it.toMovement() }
        }
        def result = day.runSimulation(input).depthTimesHorizontal()

        then:
        result == 150
    }

    def "part 2 example works"() {
        given:
        def day = new Day02()

        when:
        def input = use(Day02Kt) {
            example.collect { it.toMovement() }
        }
        def result = day.runSimulation2(input).depthTimesHorizontal()

        then:
        result == 900
    }
}
