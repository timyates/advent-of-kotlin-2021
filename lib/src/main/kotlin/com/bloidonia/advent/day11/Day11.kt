package com.bloidonia.advent.day11

import com.bloidonia.advent.readList

data class Octopus(val x: Int, val y: Int, var power: Int, var willFlash: Boolean = false)

data class OctopusGrid(val width: Int, val octopi: List<Octopus>) {
    constructor(width: Int, octopi: IntArray) : this(
        width,
        octopi.mapIndexed { idx, power -> Octopus(idx.mod(width), idx / width, power) }
    )

    private val height: Int
        get() {
            return octopi.size / width
        }

    fun update(): Long {
        octopi.forEach {
            if (++it.power > 9) {
                it.willFlash = true
            }
        }

        val flashers = ArrayDeque(octopi.filter { it.willFlash })
        while (flashers.isNotEmpty()) {
            val next = flashers.removeFirst()
            flashers.addAll(trigger(next))
        }
        var flashes = 0L
        octopi.forEach {
            if (it.power > 9) {
                it.power = 0
                flashes++
            }
            it.willFlash = false
        }
        return flashes
    }

    fun update(steps: Int): Long = (1..steps).sumOf { update() }

    fun allFlashStep(): Int = generateSequence(0) { update(); it + 1 }.first { octopi.all { it.power == 0 } }

    private fun trigger(next: Octopus): List<Octopus> {
        val result = mutableListOf<Octopus>()
        listOf(-1, 0, 1).forEach { dx ->
            listOf(-1, 0, 1).forEach { dy ->
                octopusAt(next.x + dx, next.y + dy)?.apply {
                    if (!willFlash) {
                        power++
                        if (power > 9) {
                            willFlash = true
                            result.add(this)
                        }
                    }
                }
            }
        }
        return result
    }

    private fun octopusAt(x: Int, y: Int): Octopus? = if (x in 0 until width && y in 0 until height) {
        octopi[y * width + x]
    } else {
        null
    }

    override fun toString(): String =
        octopi.chunked(width).joinToString("\n") { o -> o.map { it.power }.joinToString("") }
}

fun List<String>.readOctopi() = OctopusGrid(first().length, joinToString("").map { "$it".toInt() }.toIntArray())

fun main() {
    println(readList("/day11input.txt").readOctopi().update(100))
    println(readList("/day11input.txt").readOctopi().allFlashStep())
}