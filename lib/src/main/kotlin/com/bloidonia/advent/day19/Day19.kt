package com.bloidonia.advent.day19

import java.lang.StrictMath.pow
import kotlin.math.sqrt

data class Vector(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y, z + other.z)
    operator fun times(other: Vector) = Vector(x * other.x, y * other.y, z * other.y)
    fun distance(other: Vector) = sqrt(
        pow(other.x - x.toDouble(), 2.0)
                + pow(other.y - y.toDouble(), 2.0)
                + pow(other.z - z.toDouble(), 2.0)
    )
}

fun <T, S> Collection<T>.cartesianProduct(other: Iterable<S>): List<Pair<T, S>> {
    return cartesianProduct(other) { first, second -> first to second }
}

fun <T, S, V> Collection<T>.cartesianProduct(other: Iterable<S>, transformer: (first: T, second: S) -> V): List<V> {
    return this.flatMap { first -> other.map { second -> transformer.invoke(first, second) } }
}

class Scanner(val readings: List<Vector>) {
    fun distance(index: Int) = readings.map {
        readings[index].distance(it)
    }
}

fun String.toScanners() = this.split("\n\n").map {
    Scanner(readings =
        it.split("\n")
            .drop(1)
            .map { line -> line.split(",").let { (x, y, z) -> Vector(x.toInt(), y.toInt(), z.toInt()) } }
    )
}
