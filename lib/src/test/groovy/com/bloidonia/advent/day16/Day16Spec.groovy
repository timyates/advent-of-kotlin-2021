package com.bloidonia.advent.day16

import spock.lang.Specification

class Day16Spec extends Specification {

    def "can convert hex #input to bin"() {
        expect:
        use(Day16Kt) { input.toBin() } == expected

        where:
        input | expected
        '0'   | '0000'
        '1'   | '0001'
        '2'   | '0010'
        '3'   | '0011'
        '4'   | '0100'
        '5'   | '0101'
        '6'   | '0110'
        '7'   | '0111'
        '8'   | '1000'
        '9'   | '1001'
        'A'   | '1010'
        'B'   | '1011'
        'C'   | '1100'
        'D'   | '1101'
        'E'   | '1110'
        'F'   | '1111'
        'EF'  | '11101111'
        '26C' | '001001101100'
    }

    def "first example"() {
        when:
        def parsed = use(Day16Kt) { "D2FE28".parse() }

        then:
        parsed.version == 6
        parsed.type == 4
        parsed instanceof Literal
        parsed.value == 2021
    }

    def "first operator example"() {
        when:
        def parsed = use(Day16Kt) { "38006F45291200".parse() }

        then:
        parsed.version == 1
        parsed.type == 6
        parsed instanceof Operator
        parsed.contents.size == 2
        parsed.versionSum() == 9
    }

    def "second operator example"() {
        when:
        def parsed = use(Day16Kt) { "EE00D40C823060".parse() }

        then:
        parsed.version == 7
        parsed.type == 3
        parsed instanceof Operator
        parsed.contents.size == 3
        parsed.versionSum() == 14
    }

    def "part 1 version sum examples"() {
        expect:
        use(Day16Kt) { "8A004A801A8002F478".parse() }.versionSum() == 16
        use(Day16Kt) { "620080001611562C8802118E34".parse() }.versionSum() == 12
        use(Day16Kt) { "C0015000016115A2E0802F182340".parse() }.versionSum() == 23
        use(Day16Kt) { "A0016C880162017C3686B18A3D4780".parse() }.versionSum() == 31
    }

    def "part 2 examples"() {
        expect:
        use(Day16Kt) { "C200B40A82".parse() }.run() == 3
        use(Day16Kt) { "04005AC33890".parse() }.run() == 54
        use(Day16Kt) { "880086C3E88112".parse() }.run() == 7
        use(Day16Kt) { "CE00C43D881120".parse() }.run() == 9
        use(Day16Kt) { "D8005AC2A8F0".parse() }.run() == 1
        use(Day16Kt) { "F600BC2D8F".parse() }.run() == 0
        use(Day16Kt) { "9C005AC2F8F0".parse() }.run() == 0
        use(Day16Kt) { "9C0141080250320F1802104A08".parse() }.run() == 1
    }
}
