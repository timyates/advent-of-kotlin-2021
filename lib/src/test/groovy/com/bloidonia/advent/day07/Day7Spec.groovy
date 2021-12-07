package com.bloidonia.advent.day07

import spock.lang.Specification

import static com.bloidonia.advent.day07.Day07Kt.fuel
import static com.bloidonia.advent.day07.Day07Kt.integerSumCalc

class Day7Spec extends Specification {

    def example = [16, 1, 2, 0, 4, 2, 7, 1, 2, 14]

    def "part 1 example works as expected"() {
        expect:
        fuel(example, 2) { it } == 37
        fuel(example, 1) { it } == 41
        fuel(example, 3) { it } == 39
        fuel(example, 10) { it } == 71
    }

    def "fuel cost calculation is correct"() {
        expect:
        integerSumCalc(1) == 1
        integerSumCalc(2) == 1 + 2
        integerSumCalc(3) == 1 + 2 + 3
        integerSumCalc(4) == 1 + 2 + 3 + 4
        integerSumCalc(5) == 1 + 2 + 3 + 4 + 5
    }

    def "part 2 example works as expected"() {
        expect:
        fuel(example, 2, Day07Kt.&integerSumCalc) == 206
    }
}
