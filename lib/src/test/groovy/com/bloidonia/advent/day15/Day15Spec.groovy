package com.bloidonia.advent.day15

import kotlin.Pair
import spock.lang.Specification

class Day15Spec extends Specification {

    def example = [
            '1163751742',
            '1381373672',
            '2136511328',
            '3694931569',
            '7463417111',
            '1319128137',
            '1359912421',
            '3125421639',
            '1293138521',
            '2311944581',
    ]

    def "part 1"() {
        when:
        def cave = use(Day15Kt) { example.toCave() }
        def result = cave.search(1)
        then:
        result == 40
    }

    def "scaled lookup works as expected"() {
        when:
        def cave = use(Day15Kt) { example.toCave() }

        then:
        cave.cost(new Pair(10, 6), 5) == 2
        cave.cost(new Pair(20, 6), 5) == 3
    }

    def "test 1x1 grid"() {
        when:
        def cave = use(Day15Kt) { ['8'].toCave() }
        def result = (0..<5).collectMany { y ->
            (0..<5).collect { x -> cave.cost(new Pair(x, y), 5) }
        }

        then:
        result == [
                8, 9, 1, 2, 3,
                9, 1, 2, 3, 4,
                1, 2, 3, 4, 5,
                2, 3, 4, 5, 6,
                3, 4, 5, 6, 7,
        ]
    }

    def "part 2"() {
        when:
        def cave = use(Day15Kt) { example.toCave() }
        def result = cave.search(5)

        then:
        result == 315
    }
}
