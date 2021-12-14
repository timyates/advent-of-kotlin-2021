package com.bloidonia.advent.day14

import com.bloidonia.advent.readText

class Template(val polymer: List<String>, val insertions: Map<List<String>, List<String>>) {
    fun insertionFor(pair: List<String>) = insertions.getOrDefault(pair, listOf())

    fun step() = Template(
        polymer.windowed(2)
            .flatMap { pair -> listOf(pair.first()) + insertionFor(pair) }
            .toList() + polymer.last(),
        insertions
    )

    fun part1() = polymer.groupingBy { it }.eachCount().let { grouped ->
        grouped.maxOf { it.value } - grouped.minOf { it.value }
    }
}

fun String.parsePolymers() = this.split("\n\n".toPattern(), limit = 2).let { (template, insertions) ->
    Template(
        template.chunked(1),
        insertions.split("\n").map { tr ->
            tr.split(" -> ".toPattern(), limit = 2).let { (src, ins) ->
                src.chunked(1) to ins.chunked(1)
            }
        }.toMap()
    )
}

fun main() {
    println(generateSequence(readText("/day14input.txt").parsePolymers()) { it.step() }.drop(10).first().part1())
}