package com.bloidonia.advent.day03

import com.bloidonia.advent.readList

data class Diagnostic(val bits: List<Boolean>) {
    fun isOn(bit: Int) = bits[bit]
    fun isOn(bit: Int, required: Boolean) = bits[bit] == required
    fun toInt() = bits.toInt()
    override fun toString(): String {
        return bits.fold("") { acc, boo -> acc + (if (boo) "1" else "0") } + " (" + toInt() + ")"
    }
}

fun List<Boolean>.toInt() =
    this.foldIndexed(0) { idx, acc, current -> if (current) acc.or(1.shl(this.size - 1 - idx)) else acc }

private fun List<Diagnostic>.mostCommonAtOffset(offset: Int) = this.count { it.isOn(offset) } > this.size / 2.0

private fun bitwiseInverse(number: Int, bitWidth: Int) = 1.shl(bitWidth).minus(1).xor(number)

fun String.toDiagnostic() = Diagnostic(this.fold(listOf()) { list, ch -> list + (ch == '1') })

fun List<Diagnostic>.findOne(fn: (Int, Double) -> Boolean): Diagnostic = findOne(this, 0, fn)

tailrec fun findOne(current: List<Diagnostic>, index: Int, fn: (Int, Double) -> Boolean): Diagnostic {
    return if (current.size < 2) {
        current.first()
    } else {
        findOne(
            current.filter { it.isOn(index, fn.invoke(current.count { it.isOn(index) }, current.size / 2.0)) },
            index + 1,
            fn
        )
    }
}

fun List<Diagnostic>.gammaTimesEpsilon(): Int = this[0].bits.size.let { bitWidth ->
    (0 until bitWidth).map(this::mostCommonAtOffset).toInt().let { it * bitwiseInverse(it, bitWidth) }
}

fun List<Diagnostic>.part2() = this.findOne { a, b -> a >= b }.toInt() * this.findOne { a, b -> a < b }.toInt()

fun main() {
    println(readList("/day03input.txt") { it.toDiagnostic() }.gammaTimesEpsilon())
    println(readList("/day03input.txt") { it.toDiagnostic() }.part2())
}
