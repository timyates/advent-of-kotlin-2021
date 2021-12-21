package com.bloidonia.advent.day20

import com.bloidonia.advent.readText

data class Position(val x: Int, val y: Int) {
    operator fun plus(pos: Position) = Position(x + pos.x, y + pos.y)
}

class TrenchMap(val inverted: Boolean, val lookups: List<Int>, val settings: Set<Position>) {
    fun xRange(): IntRange = (settings.minOf { it.x } - 1)..(settings.maxOf { it.x } + 1)
    fun yRange(): IntRange = (settings.minOf { it.y } - 1)..(settings.maxOf { it.y } + 1)
    fun lookup(pos: Position, invert: Boolean) = (if (invert) Pair("0", "1") else Pair("1", "0")).let {
        lookups[((if (settings.contains(pos + Position(-1, -1))) it.first else it.second) +
                (if (settings.contains(pos + Position(0, -1))) it.first else it.second) +
                (if (settings.contains(pos + Position(1, -1))) it.first else it.second) +
                (if (settings.contains(pos + Position(-1, 0))) it.first else it.second) +
                (if (settings.contains(pos + Position(0, 0))) it.first else it.second) +
                (if (settings.contains(pos + Position(1, 0))) it.first else it.second) +
                (if (settings.contains(pos + Position(-1, 1))) it.first else it.second) +
                (if (settings.contains(pos + Position(0, 1))) it.first else it.second) +
                (if (settings.contains(pos + Position(1, 1))) it.first else it.second)).toInt(2)]
    }

    fun tick(invert: Boolean) = TrenchMap(
        invert,
        lookups,
        yRange().flatMap { y ->
            xRange().mapNotNull { x ->
                Position(x, y).let { if (lookup(it, invert) > 0) it else null }
            }
        }.toSet()
    )

    override fun toString() = yRange().joinToString("\n") { y ->
        xRange().joinToString("") { x ->
            if (settings.contains(Position(x, y))) {
                if (inverted) "." else "#"
            } else {
                if (inverted) "#" else "."
            }
        }
    }
}

fun String.toTrenchMap() = this.split("\n\n", limit = 2).let { (lookups, settings) ->
    TrenchMap(
        false,
        lookups.map { if (it == '#') 1 else 0 },
        settings.lines()
            .flatMapIndexed { y, row -> row.mapIndexedNotNull { x, ch -> if (ch == '#') Position(x, y) else null } }
            .toSet()
    )
}

fun main() {
    val part1 = readText("/day20input.txt").toTrenchMap().tick(true).apply { println(this) }.tick(false)
        .apply { println("-----\n$this") }
        .tick(true)
        .apply { println("-----\n$this") }
    println(readText("/day20input.txt").toTrenchMap().tick(true).tick(false).settings.size)
}