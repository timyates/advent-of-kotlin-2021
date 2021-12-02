package com.bloidonia.advent.day02

import com.bloidonia.advent.BaseDay

enum class Direction {
    forward, down, up
}

data class Movement(val direction: Direction, val distance: Int)

fun String.toMovement(): Movement {
    this.split(" ").let {
        return Movement(Direction.valueOf(it.first()), it.last().toInt())
    }
}

class Day02 : BaseDay() {

    var horizontal = 0
    var depth = 0

    fun runSimulation(moves: List<Movement>): Day02 {
        moves.forEach { m ->
            when {
                m.direction == Direction.forward -> horizontal += m.distance
                m.direction == Direction.up -> depth -= m.distance
                m.direction == Direction.down -> depth += m.distance
            }
        }
        return this
    }

    fun depthTimesHorizontal() = horizontal * depth

    fun readInput() =
        readList(this.javaClass.getResourceAsStream("/day02input.txt")!!) { line -> line.toMovement() }

}

fun main() {
    // Part 1
    Day02().apply {
        println(runSimulation(readInput()).depthTimesHorizontal())
    }
}
