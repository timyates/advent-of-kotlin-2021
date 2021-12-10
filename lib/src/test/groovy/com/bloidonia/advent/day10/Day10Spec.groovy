package com.bloidonia.advent.day10

import spock.lang.Specification

class Day10Spec extends Specification {

    def example = [
            '[({(<(())[]>[[{[]{<()<>>',
            '[(()[<>])]({[<{<<[]>>(',
            '{([(<{}[<>[]}>{[]{[(<()>',
            '(((({<>}<{<{<>}{[]{[]{}',
            '[[<[([]))<([[{}[[()]]]',
            '[{[{({}]{}}([{[{{{}}([]',
            '{<[[]]>}<{[{[{[]{()[[[]',
            '[<(<(<(<{}))><([]([]()',
            '<{([([[(<>()){}]>(<<{{',
            '<{([{{}}[<[[[<>{}]]]>[]]',
    ]

    def "example works as expected"() {
        expect:
        example.findResults { s -> use(Day10Kt) { s.score() } }.sum { it.corrupt } == 26397
    }

    def "completion example too"() {
        when:
        List<Long> result = use(Day10Kt) {
            example.findResults { s -> s.score().autoComplete ?: null }
        }

        then:
        result == [
                288957L,
                5566L,
                1480781L,
                995444L,
                294L,
        ]

        and:
        use(Day10Kt) { result.median() } == 288957
    }
}
