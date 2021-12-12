package com.bloidonia.advent.day12

import spock.lang.Specification

class Day12Spec extends Specification {

    def example = [
            'start-A',
            'start-b',
            'A-c',
            'A-b',
            'b-d',
            'A-end',
            'b-end',
    ]

    def example2 = [
            'dc-end',
            'HN-start',
            'start-kj',
            'dc-start',
            'dc-HN',
            'LN-dc',
            'HN-end',
            'kj-sa',
            'kj-HN',
            'kj-dc',
    ]

    def example3 = [
            'fs-end',
            'he-DX',
            'fs-he',
            'start-DX',
            'pj-DX',
            'end-zg',
            'zg-sl',
            'zg-pj',
            'pj-he',
            'RW-he',
            'fs-DX',
            'pj-RW',
            'zg-RW',
            'start-pj',
            'he-WI',
            'zg-he',
            'pj-fs',
            'start-RW',
    ]

    def "small part1 example"() {
        when:
        def caves = use(Day12Kt) { example.parseCaves() }

        then:
        caves.allRoutes(caves.part1Check).size() == 10
    }

    def "larger part1 example"() {
        when:
        def caves = use(Day12Kt) { example2.parseCaves() }

        then:
        caves.allRoutes(caves.part1Check).size() == 19
    }

    def "even larger part1 example"() {
        when:
        def caves = use(Day12Kt) { example3.parseCaves() }

        then:
        caves.allRoutes(caves.part1Check).size() == 226
    }

    def "small part2 example"() {
        when:
        def caves = use(Day12Kt) { example.parseCaves() }

        then:
        caves.allRoutes(caves.part2Check).size() == 36
    }

    def "larger part2 example"() {
        when:
        def caves = use(Day12Kt) { example2.parseCaves() }

        then:
        caves.allRoutes(caves.part2Check).size() == 103
    }

    def "even larger part2 example"() {
        when:
        def caves = use(Day12Kt) { example3.parseCaves() }

        then:
        caves.allRoutes(caves.part2Check).size() == 3509
    }
}
