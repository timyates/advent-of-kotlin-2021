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
        bh.consume((1..256).fold(readText("/day06input.txt").toPopulation()) { pop, _ -> pop.generation() }.size())
    }

    @Benchmark
    fun array(bh: Blackhole) {
        bh.consume((1..256).fold(readText("/day06input.txt").toArrayPopulation()) { pop, _ -> pop.generation() }.size())
    }

    @Benchmark
    fun deque(bh: Blackhole) {
        bh.consume((1..256).fold(readText("/day06input.txt").toDequePopulation()) { pop, _ -> pop.generation() }.size())
    }
}