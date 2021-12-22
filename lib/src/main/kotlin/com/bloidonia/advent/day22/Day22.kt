package com.bloidonia.advent.day22

import com.bloidonia.advent.readList

data class Cube(val on: Boolean, val x: IntRange, val y: IntRange, val z: IntRange)

fun String.toCube() = this.split(" ", limit = 2).let { (type, ranges) ->
    ranges.split(",", limit = 3).let { (x, y, z) ->
        val xs = x.split("=", limit = 2).last().split("\\.\\.".toPattern(), limit = 2)
        val ys = y.split("=", limit = 2).last().split("\\.\\.".toPattern(), limit = 2)
        val zs = z.split("=", limit = 2).last().split("\\.\\.".toPattern(), limit = 2)
        Cube(type == "on", xs[0].toInt()..xs[1].toInt(), ys[0].toInt()..ys[1].toInt(), zs[0].toInt()..zs[1].toInt())
    }
}

class Space(private val cubes: List<Cube>) {
    fun isOn(x: Int, y: Int, z: Int): Boolean = cubes.fold(false) { isOn, cube ->
        if (cube.x.contains(x) && cube.y.contains(y) && cube.z.contains(z)) {
            cube.on
        } else {
            isOn
        }
    }
}

fun main() {
    val cubes = Space(readList("/day22input.txt") { it.toCube() })
    val part1 =
        (-50..50).flatMap { x -> (-50..50).flatMap { y -> (-50..50).map { z -> cubes.isOn(x, y, z) } } }.count { it }
    println(part1)
}