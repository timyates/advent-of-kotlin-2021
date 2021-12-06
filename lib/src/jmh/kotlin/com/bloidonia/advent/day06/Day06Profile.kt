package com.bloidonia.advent.day06

import com.bloidonia.advent.day06.toPopulation
import com.bloidonia.advent.readText
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class Day06Profile {

    @Benchmark
    fun original(bh: Blackhole) {
        bh.consume(generateSequence(readText("/day06input.txt").toPopulation()) { it.generation() }.drop(256).first().size())
    }

    @Benchmark
    fun array(bh: Blackhole) {
        bh.consume(generateSequence(readText("/day06input.txt").toArrayPopulation()) { it.generation() }.drop(256).first().size())
    }

    @Benchmark
    fun deque(bh: Blackhole) {
        bh.consume(generateSequence(readText("/day06input.txt").toDequePopulation()) { it.generation() }.drop(256).first().size())
    }
}