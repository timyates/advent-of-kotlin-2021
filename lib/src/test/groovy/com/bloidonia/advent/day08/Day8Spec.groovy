package com.bloidonia.advent.day08

import spock.lang.Specification

class Day8Spec extends Specification {

    def example = [
            "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe",
            "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc",
            "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg",
            "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb",
            "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea",
            "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb",
            "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe",
            "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef",
            "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb",
            "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce",
    ]

    def "can parse inputs"() {
        when:
        def result = use(Day08Kt) {
            example[0].toLine()
        }

        then:
        result.display*.wires == [
                'fdgacbe'.chars.toSet(),
                'cefdb'.chars.toSet(),
                'cefbgd'.chars.toSet(),
                'gcbe'.chars.toSet(),
        ]
    }

    def "can count known digits in display"() {
        when:
        def result = use(Day08Kt) {
            example.collect { it.toLine() }
        }

        then:
        result*.numberKnown().sum() == 26
    }

    def "part2 example"() {
        when:
        def result = use(Day08Kt) {
            "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf".toLine()
        }

        then:
        result.derive() == "5353"
    }

    def "larger part2"() {
        when:
        def result = use(Day08Kt) {
            example.collect { it.toLine() }
        }

        def derivation = result*.derive()

        then:
        derivation == [
                "8394",
                "9781",
                "1197",
                "9361",
                "4873",
                "8418",
                "4548",
                "1625",
                "8717",
                "4315",
        ]
    }
}
