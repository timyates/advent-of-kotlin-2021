package com.bloidonia.advent.day17

import com.bloidonia.advent.readText

private fun integerSumCalc(n: Int) = n * (n + 1) / 2

fun yRanges(speed: Int): Sequence<Int> = integerSumCalc(speed).let { initialSpeed ->
    val initial = if (speed >= 0) {
        generateSequence(speed) { it - 1 }.takeWhile { it > 0 }.map { initialSpeed - integerSumCalc(it - 1) }
    } else {
        emptySequence()
    }
    val drop = if (speed >= 0) {
        generateSequence(0) { it - 1 }.map { initialSpeed - integerSumCalc(it - 1) }
    } else {
        generateSequence(speed) { it - 1 }.map { initialSpeed - integerSumCalc(it - 1) }
    }
    return initial + drop
}

fun xRanges(speed: Int) = integerSumCalc(speed).let { initialSpeed ->
    generateSequence(speed) { it - 1 }.takeWhile { it > 0 }.map { initialSpeed - integerSumCalc(it - 1) } +
            generateSequence { initialSpeed }
}

fun validXValues(speed: Int, target: Target) =
    xRanges(speed)
        .takeWhile { it <= target.x.last }.let {
            it.any { target.x.contains(it) }
        }

fun validYValues(speed: Int, target: Target) =
    yRanges(speed)
        .takeWhile { it >= target.y.first }.let {
            it.any { target.y.contains(it) }
        }

data class Target(val x: IntRange, val y: IntRange)

fun String.toRange() =
    this.split("=", limit = 2).last().split("..", limit = 2).let { (min, max) -> IntRange(min.toInt(), max.toInt()) }

// target area: x=20..30, y=-10..-5
fun String.toTarget() =
    this.drop(13).split(", ".toPattern(), limit = 2).let { (x, y) -> Target(x.toRange(), y.toRange()) }

fun <T, S> Collection<T>.cartesianProduct(other: Iterable<S>): List<Pair<T, S>> {
    return cartesianProduct(other) { first, second -> first to second }
}

fun <T, S, V> Collection<T>.cartesianProduct(other: Iterable<S>, transformer: (first: T, second: S) -> V): List<V> {
    return this.flatMap { first -> other.map { second -> transformer.invoke(first, second) } }
}

data class Part1(val xSpeed: Int, val ySpeed: Int, val valid: Boolean, val locations: List<Pair<Int, Int>>)

fun validTrajectories(target: Target): List<Part1> {
    val xValues = (0..target.x.last)
        .dropWhile { integerSumCalc(it) < target.x.first }
        .filter { speed -> validXValues(speed, target) }
    val yValues = (-100..100)
        .filter { speed -> validYValues(speed, target) }
    return xValues.cartesianProduct(yValues)
        .map { (xSpeed, ySpeed) ->
            val locations = yRanges(ySpeed).zip(xRanges(xSpeed))
                .takeWhile { it.first >= target.y.minOf { it } && it.second <= target.x.maxOf { it } }
                .toList()
            val valid = locations
                .any { target.x.contains(it.second) && target.y.contains(it.first) }
            Part1(xSpeed, ySpeed, valid, locations)
        }
        .filter { it.valid }
}

fun part1(target: Target) = validTrajectories(target).map { it.locations.maxOf { it.first } }.maxOf { it }
fun part2(target: Target) = validTrajectories(target).size

fun main() {
    println(part1(readText("/day17input.txt").toTarget()))
    println(part2(readText("/day17input.txt").toTarget()))
}
