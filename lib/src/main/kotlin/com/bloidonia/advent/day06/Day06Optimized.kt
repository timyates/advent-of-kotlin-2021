package com.bloidonia.advent.day06

import com.bloidonia.advent.readText
import kotlin.collections.ArrayDeque

class ArrayPopulation(private val population: LongArray) {
    fun generation() = (population.copyOfRange(1, 9) + population[0]).let {
        it[6] += it[8]
        ArrayPopulation(it)
    }

    fun size() = population.sum()
}

fun String.toArrayPopulation() =
    ArrayPopulation(this.split(",").map { it.toInt() }.fold(LongArray(9) { 0L }) { arr, v -> arr[v]++; arr })

// Non-immutable but less CPU intensive
class DequeuePopulation(private val population: ArrayDeque<Long>) {
    fun generation() = population.removeFirst().let {
        population.addLast(it)
        population[6] += it
        this
    }

    fun size() = population.sum()
}

fun String.toDequeuePopulation() =
    DequeuePopulation(this.split(",").map { it.toInt() }
        .fold(ArrayDeque(LongArray(9) { 0L }.toList())) { arr, v -> arr[v]++; arr })

fun main() {
    // array
    val initial = readText("/day06input.txt").toArrayPopulation()

    generateSequence(initial) { it.generation() }.run {
        println(drop(80).first().size())
        println(drop(256).first().size())
    }

    // ArrayDequeue is mutating, so make a new starter each time
    generateSequence(readText("/day06input.txt").toDequeuePopulation()) { it.generation() }.run {
        println(drop(80).first().size())
    }

    generateSequence(readText("/day06input.txt").toDequeuePopulation()) { it.generation() }.run {
        println(drop(256).first().size())
    }
}
