package com.bloidonia.advent.day13

import spock.lang.Specification

class Day13Spec extends Specification {

    def example = '''6,10
            |0,14
            |9,10
            |0,3
            |10,4
            |4,11
            |6,0
            |6,12
            |4,1
            |0,13
            |10,12
            |3,4
            |3,0
            |8,4
            |1,10
            |2,14
            |8,10
            |9,0
            |
            |fold along y=7
            |fold along x=5'''.stripMargin()

    def "can parse and fold"() {
        when:
        def test = use(Day13Kt) { example.toDay13() }

        then:
        test.points.size() == 18
        test.folds.size() == 2

        when:
        test = test.fold()

        then:
        test.points.size() == 17

        when:
        test = test.fold()

        then:
        test.points.size() == 16

        and:
        test.toString() == '''#####
                             |#...#
                             |#...#
                             |#...#
                             |#####'''.stripMargin()
    }
}
