package com.bloidonia.advent.day07

import com.bloidonia.advent.readText
import kotlin.math.abs

fun integerSumCalc(n: Int) = n * (n + 1) / 2
fun List<Int>.fuel(target: Int, score: (Int) -> Int = { it }) = this.sumOf { score.invoke(abs(target - it)) }

fun main() {
    val input = readText("/day07input.txt").split(",").map { it.toInt() }

    println((1..input.maxOf { it }).map { input.fuel(it) }.minOf { it })
    println((1..input.maxOf { it }).map { input.fuel(it, ::integerSumCalc) }.minOf { it })
}