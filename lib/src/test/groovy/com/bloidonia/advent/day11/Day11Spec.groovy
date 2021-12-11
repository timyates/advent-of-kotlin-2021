package com.bloidonia.advent.day11

import spock.lang.Specification

class Day11Spec extends Specification {

    def example = [
            '11111',
            '19991',
            '19191',
            '19991',
            '11111',
    ]

    def example2 = [
            '5483143223',
            '2745854711',
            '5264556173',
            '6141336146',
            '6357385478',
            '4167524645',
            '2176841721',
            '6882881134',
            '4846848554',
            '5283751526',
    ]

    def "can read a grid"() {
        when:
        def grid = use(Day11Kt) {
            example.readOctopi()
        }

        def result = grid.toString()

        then:
        result == '''11111
                    |19991
                    |19191
                    |19991
                    |11111'''.stripMargin()
    }

    def "grid after one step is as expected"() {
        when:
        def grid = use(Day11Kt) {
            example.readOctopi()
        }
        grid.update()

        def result = grid.toString()

        then:
        result == '''34543
                    |40004
                    |50005
                    |40004
                    |34543'''.stripMargin()
    }

    def "grid after two steps is as expected"() {
        when:
        def grid = use(Day11Kt) {
            example.readOctopi()
        }
        grid.update()
        grid.update()

        def result = grid.toString()

        then:
        result == '''45654
                    |51115
                    |61116
                    |51115
                    |45654'''.stripMargin()
    }

    def "larger example works"() {
        when:
        def grid = use(Day11Kt) {
            example2.readOctopi()
        }
        def flashes = (1..10).collect { grid.update() }.sum()

        then:
        flashes == 204
    }

    def "larger example works for 100 steps"() {
        when:
        def grid = use(Day11Kt) {
            example2.readOctopi()
        }
        def flashes = (1..100).collect { grid.update() }.sum()

        then:
        flashes == 1656
    }
}
