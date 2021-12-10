package com.bloidonia.advent.day10

import com.bloidonia.advent.readList
import kotlin.collections.ArrayDeque

fun opener(ch: Char): Char = when (ch) {
    '}' -> '{'
    ']' -> '['
    ')' -> '('
    else -> '<'
}

private fun corruptScore(ch: Char): Int = when (ch) {
    '}' -> 1197
    ']' -> 57
    ')' -> 3
    else -> 25137
}

private fun completionScore(ch: Char): Long = when (ch) {
    '{' -> 3
    '[' -> 2
    '(' -> 1
    else -> 4
}

data class Score(val corrupt: Int, val autoComplete: Long)

fun String.score(): Score {
    val expected = ArrayDeque<Char>(this.length)
    for (ch in this) {
        when (ch) {
            '{', '(', '[', '<' -> expected.addFirst(ch)
            else -> {
                val expectedNextChar = expected.removeFirst()
                if (expectedNextChar != opener(ch)) {
                    return Score(corruptScore(ch), 0)
                }
            }
        }
    }
    return Score(0, expected.fold(0L) { acc: Long, c: Char -> acc * 5 + completionScore(c) })
}

// Only works as the input list is an odd length
fun List<Long>.median() = this.sorted()[this.size / 2]

fun main() {
    println(readList("/day10input.txt") { it.score() }.sumOf { it.corrupt })
    println(readList("/day10input.txt") { it.score().autoComplete }.filter { it > 0 }.median())
}