package com.bloidonia.advent.day06

import com.bloidonia.advent.readText

// The trick is we only need to know how many are at each of the 9 stages of life (count needs to be a Long for Part 2)
class Population(val population: Map<Int, Long>) {
    fun generation() = mutableMapOf<Int, Long>().let { nextGeneration ->
        (0..8).forEach { age ->
            population[age]?.let { popAtAge ->
                if (age == 0) {
                    // Fish at stage 0 reset to 6 and spawn new fish at 8
                    nextGeneration[6] = popAtAge
                    nextGeneration[8] = popAtAge
                } else {
                    // Otherwise, they just get older
                    nextGeneration[age - 1] = popAtAge + (nextGeneration[age - 1] ?: 0)
                }
            }
        }
        Population(nextGeneration);
    }

    fun size() = population.values.sumOf { it }
}

fun String.toPopulation() =
    Population(this.split(",").map { it.toInt() }.groupBy { it }.mapValues { it.value.size.toLong() })

fun main() {
    val initialPopulation = readText("/day06input.txt").toPopulation()

    // Part 1
    val day80 = (1..80).fold(initialPopulation) { pop, _ -> pop.generation() }
    println(day80.size())

    // Part 2
    val day256 = (1..256).fold(initialPopulation) { pop, _ -> pop.generation() }
    println(day256.size())
}
