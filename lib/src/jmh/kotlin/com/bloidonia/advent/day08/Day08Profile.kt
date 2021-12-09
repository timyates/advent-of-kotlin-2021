package com.bloidonia.advent.day08

import com.bloidonia.advent.readList
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class Day08Profile {

    @Benchmark
    fun bruteforce(bh: Blackhole) {
        bh.consume(readList("/day08input.txt") { it.toLine() }.sumOf { it.derive().toLong() })
    }

}