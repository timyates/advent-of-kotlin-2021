package com.bloidonia.advent.day01

import com.bloidonia.advent.BaseDay

class Day01 : BaseDay() {

    fun countIncrements(integers: List<Int>): Int =
        integers.windowed(2).filter { it.last() > it.first() }.count()

    fun windowSums(integers: List<Int>): List<Int> =
        integers.windowed(3).map { it.sum() }

    fun readInput(): List<Int> = readList(this.javaClass.getResourceAsStream("/day01input.txt")!!) { line -> line.toInt() }

}

fun main() {
    // Part 1
    Day01().apply {
        println(countIncrements(readInput()))
    }

    // Part 2
    Day01().apply {
        println(countIncrements(windowSums(readInput())))
    }
}
