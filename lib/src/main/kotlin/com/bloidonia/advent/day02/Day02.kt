package com.bloidonia.advent.day02

import com.bloidonia.advent.BaseDay

enum class Direction {
    forward, down, up
}

data class Movement(val direction: Direction, val distance: Int)

data class CurrentPosition(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0) {
    fun apply(movement: Movement) = when (movement.direction) {
        Direction.up -> CurrentPosition(horizontal, depth - movement.distance, aim)
        Direction.down -> CurrentPosition(horizontal, depth + movement.distance, aim)
        Direction.forward -> CurrentPosition(horizontal + movement.distance, depth, aim)
    }

    fun apply2(movement: Movement) = when (movement.direction) {
        Direction.up -> CurrentPosition(horizontal, depth, aim - movement.distance)
        Direction.down -> CurrentPosition(horizontal, depth, aim + movement.distance)
        Direction.forward -> CurrentPosition(horizontal + movement.distance, depth + (aim * movement.distance), aim)
    }

    fun depthTimesHorizontal() = horizontal * depth
}

fun String.toMovement(): Movement {
    this.split(" ", limit = 2).let { (direction, distance) ->
        return Movement(Direction.valueOf(direction), distance.toInt())
    }
}

class Day02 : BaseDay() {
    fun runSimulation(moves: List<Movement>): CurrentPosition = moves.fold(CurrentPosition()) { acc, next -> acc.apply(next) }
    fun runSimulation2(moves: List<Movement>): CurrentPosition = moves.fold(CurrentPosition()) { acc, next -> acc.apply2(next) }

    fun readInput() =
        readList(this.javaClass.getResourceAsStream("/day02input.txt")!!) { line -> line.toMovement() }
}

fun main() {
    // Part 1
    Day02().apply {
        println(runSimulation(readInput()).depthTimesHorizontal())
    }

    // Part 2
    Day02().apply {
        println(runSimulation2(readInput()).depthTimesHorizontal())
    }
}
