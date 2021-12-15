package com.bloidonia.advent.day15

import com.bloidonia.advent.readList
import java.util.PriorityQueue
import kotlin.math.abs

typealias Position = Pair<Int, Int>

operator fun Position.plus(pos: Position) = Position(this.first + pos.first, this.second + pos.second)

class Cave(private val width: Int, private val costs: IntArray) {
    private val height by lazy { costs.size / width }
    private fun inCave(pos: Position, scale: Int = 1) =
        pos.first in 0 until width * scale && pos.second in 0 until height * scale

    private fun offset(pos: Position, scale: Int = 1) =
        if (inCave(pos, scale)) pos.second.mod(height) * width + pos.first.mod(width) else -1

    // Costs increment for every repetition of the grid in both x and 9
    fun cost(pos: Position, scale: Int = 1): Int =
        (costs[offset(pos, scale)] + (pos.first / width) + (pos.second / height)).let {
            if (it > 9) it.mod(10) + 1 else it
        }

    private fun distance(start: Position, finish: Position): Int {
        val dx = abs(start.first - finish.first)
        val dy = abs(start.second - finish.second)
        return (dx + dy) + (-2) * minOf(dx, dy)
    }

    // A* search
    fun search(scale: Int = 1): Int {
        val start = Position(0, 0)
        val end = Position(width * scale - 1, height * scale - 1)

        val done = mutableSetOf<Position>()

        val totalCost = mutableMapOf(start to 0)
        val costGuess = mutableMapOf(start to distance(start, end))
        val todo = PriorityQueue<Position>(Comparator.comparingInt { costGuess.getValue(it) }).apply {
            add(start)
        }

        while (todo.isNotEmpty()) {
            val currentPos = todo.poll()

            if (currentPos == end) return costGuess.getValue(end)

            done.add(currentPos)

            neighbours(currentPos, scale).filterNot { done.contains(it) }.forEach { neighbour ->
                val score = totalCost.getValue(currentPos) + cost(neighbour, scale)
                if (score < totalCost.getOrDefault(neighbour, Int.MAX_VALUE)) {
                    totalCost[neighbour] = score
                    costGuess[neighbour] = score + distance(neighbour, end)
                    todo.offer(neighbour)
                }
            }
        }
        return -1
    }

    // Right, left, down and up neighbors that are still in the cave
    private fun neighbours(position: Position, scale: Int = 1): List<Position> =
        listOf(Position(1, 0), Position(-1, 0), Position(0, 1), Position(0, -1)).map { position + it }
            .filter { inCave(it, scale) }
}

fun List<String>.toCave() = Cave(first().length, joinToString("").map { "$it".toInt() }.toIntArray())

fun main() {
    println(readList("/day15input.txt").toCave().search())
    println(readList("/day15input.txt").toCave().search(5))
}