package com.bloidonia.advent.day12

import com.bloidonia.advent.readList

fun String.isUpper() = this.first().isUpperCase()
fun String.isLower() = this.first().isLowerCase()

class CaveSystem(private val routes: Map<String, List<String>>) {
    fun next(path: List<String>, check: (List<String>, String) -> Boolean) =
        routes[path.last()]
            ?.filter { cave -> check(path, cave) }
            ?.filter { cave -> cave != "start" }
            ?.map { path + it }
            ?: listOf()

    val part1Check = { path: List<String>, cave: String -> cave.isUpper() || path.count { it == cave } < 1 }
    val part2Check = { path: List<String>, cave: String ->
        part1Check(path, cave) || !path.filter { it.isLower() }.groupingBy { it }.eachCount().any { it.value > 1 }
    }

    fun allRoutes(check: (List<String>, String) -> Boolean): List<List<String>> {
        var listRoutes = listOf(listOf("start"))
        val result = mutableListOf<List<String>>()
        while (listRoutes.isNotEmpty()) {
            listRoutes = listRoutes.flatMap {
                next(it, check)
            }
            result += listRoutes.filter { it.contains("end") }
            listRoutes = listRoutes.filter { !it.contains("end") }
        }
        return result.distinct()
    }

    private fun mustPassASmallCave(route: List<String>) = route.any { it.isLower() && it != "end" }

    fun part1(): Int = allRoutes(part1Check).filter(::mustPassASmallCave).size
    fun part2() = allRoutes(part2Check).filter(::mustPassASmallCave).size
}

fun List<String>.parseCaves() = CaveSystem(this.map { it.split("-", limit = 2) }.let { splits ->
    splits.flatten().distinct().fold(mutableMapOf()) { acc, s ->
        acc[s] = splits.filter { it.contains(s) }.flatten().distinct().minus(s); acc
    }
})

fun main() {
    println(readList("/day12input.txt").parseCaves().part1())
    println(readList("/day12input.txt").parseCaves().part2())
}