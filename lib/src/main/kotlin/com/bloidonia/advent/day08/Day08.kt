package com.bloidonia.advent.day08

import com.bloidonia.advent.readList

data class Digit(val wires: Set<Char>)
data class Line(val digits: List<Digit>, val display: List<Digit>) {
    fun numberKnown(): Int = display.count { it.wires.size in listOf(2, 3, 4, 7) }
    fun derive(): String {
        val resolved = Array<Digit?>(10) { null }
        resolved[1] = digits.first { it.wires.size == 2 }
        resolved[4] = digits.first { it.wires.size == 4 }
        resolved[7] = digits.first { it.wires.size == 3 }
        resolved[8] = digits.first { it.wires.size == 7 }
        resolved[0] = digits.filter {
            it != resolved[1]!! && it != resolved[4]!! && it != resolved[7]!! && it != resolved[8]!!
                    && it.wires.containsAll(resolved[1]!!.wires)
                    && it.wires.containsAll(resolved[7]!!.wires)
                    && it.wires.intersect(resolved[4]!!.wires).size == 3
                    && it.wires.size == 6
        }.first()
        resolved[2] = digits.filter {
            it != resolved[1]!! && it != resolved[4]!! && it != resolved[7]!! && it != resolved[8]!! && it != resolved[0]!!
                    && it.wires.intersect(resolved[1]!!.wires).size == 1
                    && it.wires.intersect(resolved[7]!!.wires).size == 2
                    && it.wires.intersect(resolved[4]!!.wires).size == 2
                    && it.wires.intersect(resolved[0]!!.wires).size == 4
                    && it.wires.size == 5
        }.first()
        resolved[3] = digits.filter {
            it != resolved[1]!! && it != resolved[4]!! && it != resolved[7]!! && it != resolved[8]!! && it != resolved[0]!! && it != resolved[2]!!
                    && it.wires.intersect(resolved[1]!!.wires).size == 2
                    && it.wires.intersect(resolved[7]!!.wires).size == 3
                    && it.wires.intersect(resolved[4]!!.wires).size == 3
                    && it.wires.intersect(resolved[0]!!.wires).size == 4
                    && it.wires.intersect(resolved[2]!!.wires).size == 4
                    && it.wires.size == 5
        }.first()
        resolved[5] = digits.filter {
            it != resolved[1]!! && it != resolved[4]!! && it != resolved[7]!! && it != resolved[8]!! && it != resolved[0]!! && it != resolved[2]!! && it != resolved[3]!!
                    && it.wires.intersect(resolved[1]!!.wires).size == 1
                    && it.wires.intersect(resolved[7]!!.wires).size == 2
                    && it.wires.intersect(resolved[4]!!.wires).size == 3
                    && it.wires.intersect(resolved[0]!!.wires).size == 4
                    && it.wires.intersect(resolved[2]!!.wires).size == 3
                    && it.wires.intersect(resolved[3]!!.wires).size == 4
                    && it.wires.size == 5
        }.first()
        resolved[6] = digits.filter {
            it != resolved[1]!! && it != resolved[4]!! && it != resolved[7]!! && it != resolved[8]!! && it != resolved[0]!! && it != resolved[2]!! && it != resolved[3]!! && it != resolved[5]!!
                    && it.wires.intersect(resolved[1]!!.wires).size == 1
                    && it.wires.intersect(resolved[7]!!.wires).size == 2
                    && it.wires.intersect(resolved[4]!!.wires).size == 3
                    && it.wires.intersect(resolved[0]!!.wires).size == 5
                    && it.wires.intersect(resolved[2]!!.wires).size == 4
                    && it.wires.intersect(resolved[3]!!.wires).size == 4
                    && it.wires.intersect(resolved[5]!!.wires).size == 5
                    && it.wires.size == 6
        }.first()
        resolved[9] = digits.filter {
            it != resolved[1]!! && it != resolved[4]!! && it != resolved[7]!! && it != resolved[8]!! && it != resolved[0]!! && it != resolved[2]!! && it != resolved[3]!! && it != resolved[5]!! && it != resolved[6]
                    && it.wires.intersect(resolved[1]!!.wires).size == 2
                    && it.wires.intersect(resolved[7]!!.wires).size == 3
                    && it.wires.intersect(resolved[4]!!.wires).size == 4
                    && it.wires.intersect(resolved[0]!!.wires).size == 5
                    && it.wires.intersect(resolved[2]!!.wires).size == 4
                    && it.wires.intersect(resolved[3]!!.wires).size == 5
                    && it.wires.intersect(resolved[5]!!.wires).size == 5
                    && it.wires.intersect(resolved[6]!!.wires).size == 5
                    && it.wires.size == 6
        }.first()
        return display.map { resolved.indexOf(it).toString() }.joinToString(separator = "")
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
    println(readList("/day08input.txt") { it.toLine() }.map { it.derive().toLong() }.sum())
}