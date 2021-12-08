package com.bloidonia.advent.day08

import com.bloidonia.advent.readList

data class Digit(val wires: Set<Char>) {
    private fun intersections(vararg other: Digit) = other.map { wires.intersect(it.wires).size }.toList()
    fun matches(expectedSegmentCount: Int, known: Array<Digit>, expectedOverlap: List<Int>) =
        !known.contains(this)
                && this.wires.size == expectedSegmentCount
                && intersections(known[1], known[4], known[7]) == expectedOverlap
}
data class Line(val digits: List<Digit>, val display: List<Digit>) {
    fun numberKnown(): Int = display.count { it.wires.size in listOf(2, 3, 4, 7) }
    fun derive(): String {
        val resolved = Array(10) { Digit(setOf()) }
        resolved[1] = digits.first { it.wires.size == 2 }
        resolved[4] = digits.first { it.wires.size == 4 }
        resolved[7] = digits.first { it.wires.size == 3 }
        resolved[8] = digits.first { it.wires.size == 7 }
        resolved[0] = digits.first { it.matches(6, resolved, listOf(2, 3, 3)) }
        resolved[2] = digits.first { it.matches(5, resolved, listOf(1, 2, 2)) }
        resolved[3] = digits.first { it.matches(5, resolved, listOf(2, 3, 3)) }
        resolved[5] = digits.first { it.matches(5, resolved, listOf(1, 3, 2)) }
        resolved[6] = digits.first { it.matches(6, resolved, listOf(1, 3, 2)) }
        resolved[9] = digits.first { it.matches(6, resolved, listOf(2, 4, 3)) }

        return display.joinToString(separator = "") { resolved.indexOf(it).toString() }
    }
}

fun String.toDigit() = Digit(this.toCharArray().toSet())
fun String.toLine(): Line = this.split("|", limit = 2).let { (lhs, rhs) ->
    Line(
        lhs.trim().split(" ").map { it.toDigit() }.toList(),
        rhs.trim().split(" ").map { it.toDigit() }.toList()
    )
}

fun main() {
    // Part 1
    println(readList("/day08input.txt") { it.toLine() }.sumOf { it.numberKnown() })
    // Part 2
    println(readList("/day08input.txt") { it.toLine() }.sumOf { it.derive().toLong() })
}