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

// This is my tests this time...
fun example() {
    val die = Die()
    generateSequence(Game(die, 0, listOf(4 - 1, 8 - 1).toIntArray())) { it.roll() }
        .dropWhile { game -> game.scores.all { it < 1000 } }
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
        .dropWhile { game -> game.scores.all { it < 1000 } }
        .first()
        .apply {
            println(this.turn * 3 * this.scores.find { it < 1000 }!!)
        }
}

data class Player(val pos: Int, val score: Int)
data class Win(val player1: Long, val player2: Long)

val dirac = (1..3).flatMap { a -> (1..3).flatMap { b -> (1..3).map { c -> a + b + c } } }

// I miss Groovy's @Memoized
fun <X, R> cacheBiFunction(fn: (X, X) -> R): (X, X) -> R {
    val cache: MutableMap<Pair<X, X>, R> = HashMap()
    return { a, b ->
        cache.getOrPut(Pair(a, b)) { fn(a, b) }
    }
}

val cachedPart2 = cacheBiFunction(::part2)

fun part2(a: Player, b: Player): Win {
    if (a.score >= 21) {
        return Win(1, 0)
    }
    if (b.score >= 21) {
        return Win(0, 1)
    }
    var myWins = 0L
    var theirWins = 0L
    for (die in dirac) {
        val pos = (a.pos + die) % 10
        val (other, me) = cachedPart2(b, Player(pos, a.score + pos + 1))
        myWins += me
        theirWins += other
    }
    return Win(myWins, theirWins)
}

fun main() {
    example()
    part1()
    println(dirac)
    println(cachedPart2(Player(8 - 1, 0), Player(2 - 1, 0)))
}