package com.bloidonia.advent.day14

import com.bloidonia.advent.readText

class Template(
    private val initialChar: Char,
    private val polymer: Map<String, Long>,
    private val insertions: Map<String, List<String>>
) {
    fun step(): Template {
        val next = mutableMapOf<String, Long>()
        polymer.forEach { entry ->
            val inserts = insertions[entry.key]
            if (inserts != null) {
                inserts.forEach {
                    next[it] = (next[it] ?: 0L) + entry.value
                }
            } else {
                next[entry.key] = entry.value
            }
        }
        return Template(initialChar, next, insertions)
    }

    // Count is the initial char, plus the number of last chars in all the pairs
    fun count() = buildMap<Char, Long> {
        put(initialChar, 1L)
        polymer.forEach { entry ->
            put(entry.key.last(), getOrDefault(entry.key.last(), 0L) + entry.value)
        }
    }.let { map ->
        map.maxOf { it.value } - map.minOf { it.value }
    }
}

fun String.parsePolymers() = this.split("\n\n".toPattern(), limit = 2).let { (template, insertions) ->
    Template(
        this.first(),
        template.windowed(2).groupingBy { it }.eachCount().map { it.key to it.value.toLong() }.toMap(),
        insertions.split("\n").associate { tr ->
            tr.split(" -> ".toPattern(), limit = 2).let { (src, ins) ->
                src to listOf(src.first() + ins, ins + src.last())
            }
        }
    )
}

fun main() {
    println(generateSequence(readText("/day14input.txt").parsePolymers()) { it.step() }.drop(10).first().count())
    println(generateSequence(readText("/day14input.txt").parsePolymers()) { it.step() }.drop(40).first().count())
}