package com.bloidonia.advent.day09

import com.bloidonia.advent.readList

data class Point(val x: Int, val y: Int)

data class Cave(val width: Int, val z: IntArray) {
    private fun p(x: Int, y: Int) =
        if (x < 0 || y < 0 || x >= width || y >= z.size / width) Int.MAX_VALUE else z[x + (y * width)]

    private fun minima() = (0 until z.size / width).flatMap { y ->
        (0 until width).mapNotNull { x ->
            val me = p(x, y)
            if (p(x - 1, y) > me && p(x + 1, y) > me && p(x, y - 1) > me && p(x, y + 1) > me)
                Point(x, y)
            else
                null
        }
    }

    fun basin(seed: Point): Int {
        val queue = ArrayDeque(listOf(seed))
        val seen = mutableSetOf<Point>()
        var size = 0
        while (!queue.isEmpty()) {
            val next = queue.removeFirst()
            if (seen.contains(next)) {
                continue
            }
            seen.add(next)
            if (p(next.x, next.y) < 9) {
                size++
                listOfNotNull(
                    if (next.x > 0) Point(next.x - 1, next.y) else null,
                    if (next.x < width - 1) Point(next.x + 1, next.y) else null,
                    if (next.y > 0) Point(next.x, next.y - 1) else null,
                    if (next.y < z.size / width) Point(next.x, next.y + 1) else null,
                )
                    .filter { !seen.contains(it) }
                    .forEach { p -> queue.addLast(p) }
            }
        }
        return size
    }

    fun part1() = minima().sumOf { p(it.x, it.y) + 1 }
    fun part2() = minima().map { basin(it) }.sortedDescending().take(3).reduce { a, b -> a * b }
}

fun List<String>.toCave() = Cave(first().length, joinToString("").map { "$it".toInt() }.toIntArray())

fun main() {
    println(readList("/day09input.txt") { it }.toCave().part1())
    println(readList("/day09input.txt") { it }.toCave().part2())
}