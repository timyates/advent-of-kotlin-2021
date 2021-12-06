package com.bloidonia.advent.day04

import com.bloidonia.advent.BaseDay
import com.bloidonia.advent.readText
import java.util.regex.Pattern

data class Square(val number: Int, var seen: Boolean = false)
data class Board(val grid: List<List<Square>>) {
    val width = grid.first().size

    private fun transpose() = Board((0 until width).map { index -> grid.map { it[index] } }.toList())
    private fun verticalWin(): Boolean = transpose().horizontalWin()
    private fun horizontalWin(): Boolean = grid.any { it.all { it.seen } }

    fun unmarkedSum() = grid.flatten().filter { !it.seen }.sumOf { it.number }

    fun hasWon() = verticalWin() or horizontalWin()
    fun mark(ball: Int) = grid.flatten().forEach { s -> s.seen = s.seen or (s.number == ball) }
}

data class BingoGame(val balls: List<Int>, val boards: List<Board>) {
    fun playPart1(): Int {
        for (ball in balls) {
            boards.forEach { it.mark(ball) }
            val winner = boards.find { it.hasWon() }
            if (winner != null) {
                return winner.unmarkedSum() * ball
            }
        }
        return 0;
    }

    fun playPart2(): Int {
        var lastBoard: Board? = null;
        for (ball in balls) {
            boards.forEach { it.mark(ball) }
            val losers = boards.filter { !it.hasWon() }
            if (losers.size == 1) {
                lastBoard = losers.first();
            }
            if (losers.isEmpty()) {
                return lastBoard!!.unmarkedSum() * ball
            }
        }
        return 0;
    }
}

fun readBoards(input: String) = input.split("\n", limit = 2).let { (balls, boardDef) ->
    val boards = boardDef
        .split("\n\n")
        .map { board ->
            Board(
                board.split("\n")
                    .filter { it != "" }
                    .map { line -> line.trim().split(Pattern.compile("\\s+")).map { Square(it.toInt()) } }
                    .toList()
            )
        }
    BingoGame(balls.split(",").map { it.toInt() }, boards)
}


fun main() {
    println(readBoards(readText("/day04input.txt")).playPart1())
    println(readBoards(readText("/day04input.txt")).playPart2())
}
