package com.bloidonia.advent.day18

import spock.lang.Specification

class Day18Spec extends Specification {
    def "quick sum check"() {
        given:
        def result = use(Day18Kt) {
            '[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]'.toSnailFish() +
                    '[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]'.toSnailFish()
        }.reduce()

        expect:
        result == use(Day18Kt) { '[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]'.toSnailFish() }

    }

    def "reductions"() {
        expect:
        use(Day18Kt) { given.toSnailFish().reduce() } == use(Day18Kt) { expected.toSnailFish() }

        where:
        given                                   | expected
        '[[[[[9,8],1],2],3],4]'                 | '[[[[0,9],2],3],4]'
        '[7,[6,[5,[4,[3,2]]]]]'                 | '[7,[6,[5,[7,0]]]]'
        '[[6,[5,[4,[3,2]]]],1]'                 | '[[6,[5,[7,0]]],3]'
        '[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]' | '[[3,[2,[8,0]]],[9,[5,[7,0]]]]'
        '[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]'     | '[[3,[2,[8,0]]],[9,[5,[7,0]]]]'
    }

    def "worked example"() {
        when:
        def result = use(Day18Kt) { '[[[[4,3],4],4],[7,[[8,4],9]]]'.toSnailFish() + '[1,1]'.toSnailFish() }.reduce()

        then:
        result == use(Day18Kt) { '[[[[0,7],4],[[7,8],[6,0]]],[8,1]]'.toSnailFish() }
    }

    def "sum 1"() {
        given:
        def input = [
                '[1,1]',
                '[2,2]',
                '[3,3]',
                '[4,4]',
        ].collect { num -> use(Day18Kt) { num.toSnailFish() } }

        when:
        def result = input.inject { acc, b -> (acc + b).reduce() }

        then:
        result == use(Day18Kt) { '[[[[1,1],[2,2]],[3,3]],[4,4]]'.toSnailFish() }
    }

    def "sum 2"() {
        given:
        def input = [
                '[1,1]',
                '[2,2]',
                '[3,3]',
                '[4,4]',
                '[5,5]',
        ].collect { num -> use(Day18Kt) { num.toSnailFish() } }

        when:
        def result = input.inject { acc, b -> (acc + b).reduce() }

        then:
        result == use(Day18Kt) { '[[[[3,0],[5,3]],[4,4]],[5,5]]'.toSnailFish() }
    }

    def "sum 3"() {
        given:
        def input = [
                '[1,1]',
                '[2,2]',
                '[3,3]',
                '[4,4]',
                '[5,5]',
                '[6,6]',
        ].collect { num -> use(Day18Kt) { num.toSnailFish() } }

        when:
        def result = input.inject { acc, b -> (acc + b).reduce() }

        then:
        result == use(Day18Kt) { '[[[[5,0],[7,4]],[5,5]],[6,6]]'.toSnailFish() }
    }

    def "mag test"() {
        given:
        def input = [
                '[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]',
                '[[[5,[2,8]],4],[5,[[9,9],0]]]',
                '[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]',
                '[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]',
                '[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]',
                '[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]',
                '[[[[5,4],[7,7]],8],[[8,3],8]]',
                '[[9,3],[[9,9],[6,[4,9]]]]',
                '[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]',
                '[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]',
        ].collect { num -> use(Day18Kt) { num.toSnailFish() } }

        when:
        def result = input.inject { acc, n -> (acc + n).reduce() }

        then:
        result.magnitude() == 4140
    }

    def "magnitude check"() {
        expect:
        use(Day18Kt) { input.toSnailFish().magnitude() } == expected

        where:
        input                                                           | expected
        '[[9,1],[1,9]]'                                                 | 129
        '[[1,2],[[3,4],5]]'                                             | 143
        '[[[[0,7],4],[[7,8],[6,0]]],[8,1]]'                             | 1384
        '[[[[1,1],[2,2]],[3,3]],[4,4]]'                                 | 445
        '[[[[3,0],[5,3]],[4,4]],[5,5]]'                                 | 791
        '[[[[5,0],[7,4]],[5,5]],[6,6]]'                                 | 1137
        '[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]'         | 3488
        '[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]' | 4140
    }
}
