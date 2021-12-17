package com.bloidonia.advent.day17

import kotlin.ranges.IntRange
import kotlin.sequences.SequencesKt
import spock.lang.Specification

import static com.bloidonia.advent.day17.Day17Kt.part1
import static com.bloidonia.advent.day17.Day17Kt.part2
import static com.bloidonia.advent.day17.Day17Kt.xRanges
import static com.bloidonia.advent.day17.Day17Kt.yRanges

class Day17Spec extends Specification {

    def "can parse input"() {
        given:
        def target = use(Day17Kt) { 'target area: x=20..30, y=-10..-5'.toTarget() }

        expect:
        target.x == new IntRange(20, 30)
        target.y == new IntRange(-10, -5)
    }

    def "can work out positions in x"() {
        expect:
        use(SequencesKt) { xRanges(2).take(4) }.collect() == [2, 3, 3, 3]
        use(SequencesKt) { xRanges(3).take(6) }.collect() == [3, 5, 6, 6, 6, 6]
        use(SequencesKt) { xRanges(10).take(12) }.collect() == [10, 19, 27, 34, 40, 45, 49, 52, 54, 55, 55, 55]
    }

    def "can work out positions in y"() {
        expect:
        use(SequencesKt) { yRanges(2).take(9) }.collect() == [2, 3, 3, 2, 0, -3, -7, -12, -18]
        use(SequencesKt) { yRanges(-1).take(5) }.collect() == [-1, -3, -6, -10, -15]
        use(SequencesKt) { yRanges(3).take(10) }.collect() == [3, 5, 6, 6, 5, 3, 0, -4, -9, -15]
    }

    def "part 1 example"() {
        given:
        def target = use(Day17Kt) { 'target area: x=20..30, y=-10..-5'.toTarget() }

        expect:
        part1(target) == 45
    }

    def "part 2 example"() {
        given:
        def target = use(Day17Kt) { 'target area: x=20..30, y=-10..-5'.toTarget() }

        expect:
        part2(target) == 112
    }
}
