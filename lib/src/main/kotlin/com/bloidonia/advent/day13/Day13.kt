package com.bloidonia.advent.day13

import com.bloidonia.advent.readText

data class Point(val x: Int, val y: Int) {
    fun reflect(axis: String, pos: Int) = when (axis) {
        "x" -> if (x < pos) this else Point(pos - (x - pos), y)
        else -> if (y < pos) this else Point(x, pos - (y - pos))
    }
}

class Fold(private val axis: String, private val pos: Int) {
    fun fold(points: List<Point>): List<Point> = points.map { it.reflect(axis, pos) }.distinct()
}

class Day13(val points: List<Point>, val folds: List<Fold>) {
    fun fold() = Day13(folds.first().fold(points), folds.drop(1))
    override fun toString() = (0..points.maxOf { it.y }).joinToString("\n") { y ->
        (0..points.maxOf { it.x }).joinToString("") { x ->
            if (points.contains(Point(x, y))) "#" else "."
        }
    }
}

fun String.toDay13() = this.split("\n\n".toPattern(), limit = 2).let { parts ->
    Day13(
        parts.first().split("\n").map { it.toPoint() },
        parts.last().split("\n").map { it.toFold() }
    )
}

fun String.toPoint() = this.split(",", limit = 2).let { Point(it.first().toInt(), it.last().toInt()) }
fun String.toFold() = this.split(" ").last().split("=", limit = 2).let { Fold(it.first(), it.last().toInt()) }

fun main() {
    println(readText("/day13input.txt").toDay13().fold().points.size)
    println(
        generateSequence(readText("/day13input.txt").toDay13()) { it.fold() }
            .dropWhile { it.folds.isNotEmpty() }
            .first()
    )
}