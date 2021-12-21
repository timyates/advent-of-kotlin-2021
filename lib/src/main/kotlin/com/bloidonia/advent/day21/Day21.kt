package com.bloidonia.advent.day21

class Die(var next: Int = 0) {
    fun next(): Int = (next + 1).apply {
        next = this.mod(100)
    }
    fun three(): Int = listOf(next(), next(), next()).sum()
}

class Game(private val die: Die, val turn: Int, private val pos: IntArray, val scores: IntArray = IntArray(2) { 0 }) {
    fun roll(): Game {
        val player = turn.mod(2)
        pos[player] = (pos[player] + die.three()).mod(10)
        scores[player] += pos[player] + 1
        return Game(die, turn + 1, pos, scores)
    }
}

fun example() {
    val die = Die()
    generateSequence(Game(die, 0, listOf(4 - 1, 8 - 1).toIntArray())) { it.roll() }
        .dropWhile { it.scores.all { it < 1000 } }
        .first()
        .apply {
            (this.turn * 3 * this.scores.find { it < 1000 }!!).apply {
                if (this != 739785) {
                    throw IllegalArgumentException("Nope $this")
                }
                println(this)
            }
        }
}

fun part1() {
    val die = Die()
    generateSequence(Game(die, 0, listOf(8 - 1, 2 - 1).toIntArray())) { it.roll() }
        .dropWhile { it.scores.all { it < 1000 } }
        .first()
        .apply {
            println(this.turn * 3 * this.scores.find { it < 1000 }!!)
        }
}

fun main() {
    example()
    part1()
}