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

fun parsePacket(s: String): Pair<Packet, String> {
    var data = s
    val takeBits = { n: Int -> data.take(n).apply { data = data.drop(n) } }

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

        return Pair(Literal(version, type, sb.toString().toLong(2)), data)
    } else {
        // it's an operator
        val opType = takeBits(1).toInt(2)
        val packets = mutableListOf<Packet>()
        if (opType == 0) {
            // length type operator
            var dataLength = takeBits(15).toInt(2)
            var dataBuffer = takeBits(dataLength)
            while (dataLength > 0) {
                val (p, remain) = parsePacket(dataBuffer)
                packets.add(p)
                dataLength -= (dataLength - remain.length)
                dataBuffer = remain
            }
            return Pair(Operator(version, type, packets), data)
        } else {
            // num packets type operator
            var packetCount = takeBits(11).toLong(2)
            while (packetCount > 0) {
                val (p, remain) = parsePacket(data)
                packetCount--
                packets.add(p)
                data = remain
            }
            return Pair(Operator(version, type, packets), data)
        }
    }
}

fun Char.toBin(): String = Character.digit(this, 16).toString(2).padStart(4, '0')
fun String.toBin(): String = this.map { it.toBin() }.joinToString("")
fun String.parse(): Packet = parsePacket(this.toBin()).first

fun main() {
    println(readText("/day16input.txt").parse().versionSum())
    println(readText("/day16input.txt").parse().run())
}