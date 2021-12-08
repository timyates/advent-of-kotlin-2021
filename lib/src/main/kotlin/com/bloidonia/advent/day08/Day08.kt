package com.bloidonia.advent.day08

import com.bloidonia.advent.readList

data class Digit(val wires: Set<Char>) {
    fun intersections(other: Digit) = wires.intersect(other.wires).size
}
data class Line(val digits: List<Digit>, val display: List<Digit>) {
    fun numberKnown(): Int = display.count { it.wires.size in listOf(2, 3, 4, 7) }
    fun derive(): String {
        val resolved = Array(10) { Digit(setOf()) }
        resolved[1] = digits.first { it.wires.size == 2 }
        resolved[4] = digits.first { it.wires.size == 4 }
        resolved[7] = digits.first { it.wires.size == 3 }
        resolved[8] = digits.first { it.wires.size == 7 }
        resolved[0] = digits.first {
            !resolved.contains(it)
                    && it.wires.size == 6
                    && it.wires.containsAll(resolved[1].wires)
                    && it.wires.containsAll(resolved[7].wires)
                    && it.intersections(resolved[4]) == 3
        }
        resolved[2] = digits.first {
            !resolved.contains(it)
                    && it.wires.size == 5
                    && it.intersections(resolved[1]) == 1
                    && it.intersections(resolved[7]) == 2
                    && it.intersections(resolved[4]) == 2
                    && it.intersections(resolved[0]) == 4
        }
        resolved[3] = digits.first {
            !resolved.contains(it)
                    && it.wires.size == 5
                    && it.intersections(resolved[1]) == 2
                    && it.intersections(resolved[7]) == 3
                    && it.intersections(resolved[4]) == 3
                    && it.intersections(resolved[0]) == 4
                    && it.intersections(resolved[2]) == 4
        }
        resolved[5] = digits.first {
            !resolved.contains(it)
                    && it.wires.size == 5
                    && it.intersections(resolved[1]) == 1
                    && it.intersections(resolved[7]) == 2
                    && it.intersections(resolved[4]) == 3
                    && it.intersections(resolved[0]) == 4
                    && it.intersections(resolved[2]) == 3
                    && it.intersections(resolved[3]) == 4
        }
        resolved[6] = digits.first {
            !resolved.contains(it)
                    && it.wires.size == 6
                    && it.intersections(resolved[1]) == 1
                    && it.intersections(resolved[7]) == 2
                    && it.intersections(resolved[4]) == 3
                    && it.intersections(resolved[0]) == 5
                    && it.intersections(resolved[2]) == 4
                    && it.intersections(resolved[3]) == 4
                    && it.intersections(resolved[5]) == 5
        }
        resolved[9] = digits.first {
            !resolved.contains(it)
                    && it.wires.size == 6
                    && it.intersections(resolved[1]) == 2
                    && it.intersections(resolved[7]) == 3
                    && it.intersections(resolved[4]) == 4
                    && it.intersections(resolved[0]) == 5
                    && it.intersections(resolved[2]) == 4
                    && it.intersections(resolved[3]) == 5
                    && it.intersections(resolved[5]) == 5
                    && it.intersections(resolved[6]) == 5
        }
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