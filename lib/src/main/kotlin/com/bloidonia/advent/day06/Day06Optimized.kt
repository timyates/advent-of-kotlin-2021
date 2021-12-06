package com.bloidonia.advent.day06

import com.bloidonia.advent.readText

class OptimizedPopulation(private val population: LongArray) {
    fun generation() = (population.copyOfRange(1, 9) + population[0]).let {
        it[6] += it[8]
        OptimizedPopulation(it)
    }

    fun size() = population.sum()
}

fun String.toPop2() =
    OptimizedPopulation(this.split(",").map { it.toInt() }.fold(LongArray(9) { 0L }) { arr, v -> arr[v]++; arr })

fun main() {
    val initial = readText("/day06input.txt").toPop2()

    generateSequence(initial) { it.generation() }.run {
        println(drop(80).first().size())
        println(drop(256).first().size())
    }
}
