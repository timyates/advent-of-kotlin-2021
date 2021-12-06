package com.bloidonia.advent.day06

import com.bloidonia.advent.readText

class Pop2(val population: Array<Long>) {
    fun generation() = population.take(1).let { birthers ->
        val newPop = (population.drop(1) + birthers).toTypedArray()
        newPop[6] += birthers[0]
        Pop2(newPop)
    }
    fun size() = population.sum()
}

fun String.toPop2() = Pop2(this.split(",").map { it.toInt() }.fold(arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)) { arr, v -> arr[v]++; arr })

fun main() {
    val pop2 = readText("/day06input.txt").toPop2()

    // Part 1
    val day80pt2 = (1..80).fold(pop2) { pop, _ -> pop.generation() }
    println(day80pt2.size())

    // Part 2
    val day256pt2 = (1..256).fold(pop2) { pop, _ -> pop.generation() }
    println(day256pt2.size())
}
