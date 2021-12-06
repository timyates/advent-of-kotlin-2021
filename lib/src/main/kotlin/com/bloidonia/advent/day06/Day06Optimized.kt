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

class DequeuePopulation(private val population: ArrayDeque<Long>) {
    fun generation() = ArrayDeque(population).run {
        removeFirst().let {
            addLast(it)
            this[6] += it
            DequeuePopulation(this)
        }
    }

    fun size() = population.sum()
}

fun String.toDequeuePopulation() =
    DequeuePopulation(this.split(",").map { it.toInt() }.fold(ArrayDeque(LongArray(9) { 0L }.toList())) { arr, v -> arr[v]++; arr })

fun main() {
    // array
    val initial = readText("/day06input.txt").toArrayPopulation()

    generateSequence(initial) { it.generation() }.run {
        println(drop(80).first().size())
        println(drop(256).first().size())
    }

    // ArrayDequeue
    val dqInitial = readText("/day06input.txt").toDequeuePopulation()

    generateSequence(dqInitial) { it.generation() }.run {
        println(drop(80).first().size())
        println(drop(256).first().size())
    }
}
