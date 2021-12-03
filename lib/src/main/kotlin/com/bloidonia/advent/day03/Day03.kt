package com.bloidonia.advent.day03

import com.bloidonia.advent.readList

data class Reading2(val bits: List<Boolean>) {
    fun isOn(bit: Int) = bits[bit]
    fun isOn(bit: Int, required: Boolean) = bits[bit] == required
    fun toInt() = bits.toInt()
    override fun toString(): String {
        return bits.fold("") { acc, boo -> acc + (if (boo) "1" else "0") } + " (" + toInt() + ")"
    }
}

fun List<Boolean>.toInt() =
    this.foldIndexed(0) { idx, acc, current -> if (current) acc.or(1.shl(this.size - 1 - idx)) else acc }

fun List<Reading2>.mostCommon(offset: Int) = this.count { it.isOn(offset) } > this.size / 2.0
fun List<Reading2>.gammaTimesEpsilon(): Int =
    (0 until this[0].bits.size).map(this::mostCommon).toInt()
        .let {
            it * 1.shl(this[0].bits.size).minus(1).xor(it)
        }

fun String.toReading2() = Reading2(
    this.fold(listOf()) { list, ch -> list + (ch == '1') }
)

fun findOne(current: List<Reading2>, index: Int, fn: (Int, Double) -> Boolean): Reading2 {
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

fun List<Reading2>.part2() =
    findOne(this, 0) { a, b -> a >= b }.toInt() * findOne(this, 0) { a, b -> a < b }.toInt()

fun main() {
    println(readList("/day03input.txt") { it.toReading2() }.gammaTimesEpsilon())
    println(readList("/day03input.txt") { it.toReading2() }.part2())
}
