package com.bloidonia.advent.day16

import com.bloidonia.advent.readText

sealed class Packet(val version: Int, val type: Int) {
    abstract fun versionSum(): Long
    abstract fun run(): Long
}

class Literal(version: Int, type: Int, private val value: Long) : Packet(version, type) {
    override fun versionSum() = version.toLong()
    override fun run() = value
}

class Operator(version: Int, type: Int, private val contents: List<Packet>) : Packet(version, type) {
    override fun versionSum() = version + contents.sumOf { it.versionSum() }
    override fun run(): Long = when (type) {
        0 -> contents.fold(0) { acc, p -> acc + p.run() }
        1 -> contents.fold(1) { acc, p -> acc * p.run() }
        2 -> contents.minOf { it.run() }
        3 -> contents.maxOf { it.run() }
        5 -> if (contents[0].run() > contents[1].run()) 1 else 0
        6 -> if (contents[0].run() < contents[1].run()) 1 else 0
        else -> if (contents[0].run() == contents[1].run()) 1 else 0
    }
}

fun Char.toBin(): String = Character.digit(this, 16).toString(2).padStart(4, '0')
fun String.toBin() = this.flatMap { it.toBin().toCharArray().toList() }

fun String.parse(): Packet = ArrayDeque(this.toBin()).let { deque ->
    fun parsePacket(): Packet {
        val takeBits = { n: Int -> (0 until n).map { deque.removeFirst() }.joinToString("") }

        val version = takeBits(3).toInt(2)
        val type = takeBits(3).toInt(2)

        if (type == 4) {
            // it's a literal
            val sb = StringBuilder()
            do {
                val chunk = takeBits(5).apply {
                    sb.append(this.substring(1))
                }
            } while (chunk[0] == '1')

            return Literal(version, type, sb.toString().toLong(2))
        } else {
            // it's an operator
            val opType = takeBits(1).toInt(2)
            val packets = mutableListOf<Packet>()
            if (opType == 0) {
                // length type operator
                var dataLength = takeBits(15).toInt(2)
                while (dataLength > 0) {
                    val original = deque.size
                    val p = parsePacket()
                    packets.add(p)
                    dataLength -= (original - deque.size)
                }
            } else {
                // num packets type operator
                var packetCount = takeBits(11).toLong(2)
                while (packetCount > 0) {
                    val p = parsePacket()
                    packetCount--
                    packets.add(p)
                }
            }
            return Operator(version, type, packets)
        }
    }

    parsePacket()
}

fun main() {
    println(readText("/day16input.txt").parse().versionSum())
    println(readText("/day16input.txt").parse().run())
}
