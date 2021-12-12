package com.bloidonia.advent.day12

import com.bloidonia.advent.readList

class CaveSystem(private val routes: Map<String, List<String>>) {
    fun next(path: List<String>, smallRoomPassageCount: Int) =
        routes[path.last()]
            ?.filter { cave -> cave.uppercase() == cave || path.count { it == cave } < smallRoomPassageCount }
            ?.filter { cave -> cave != "start" }
            ?.map { path + it }
            ?: listOf()

    fun allRoutes(smallRoomPassageCount: Int = 1): List<List<String>> {
        var listRoutes = listOf(listOf("start"))
        val result = mutableListOf<List<String>>()
        while (listRoutes.isNotEmpty()) {
            listRoutes = listRoutes.flatMap {
                next(it, smallRoomPassageCount)
            }
            result += listRoutes.filter { it.contains("end") }
            listRoutes = listRoutes.filter { !it.contains("end") }
        }
        return result.distinct()
    }

    fun part1(): Int = allRoutes().filter { it.any { it.lowercase() == it && it != "end" } }.size
    fun part2() = allRoutes(2).filter { it.any { it.lowercase() == it && it != "end" } }.size
}

fun List<String>.parseCaves() = CaveSystem(this.map { it.split("-", limit = 2) }.let { splits ->
    splits.flatten().distinct().fold(mutableMapOf()) { acc, s ->
        acc[s] = splits.filter { it.contains(s) }.flatten().distinct().minus(s); acc
    }
})

fun main() {
    println(readList("/day12input.txt").parseCaves().part1())
}