package com.bloidonia.advent.day18

import com.bloidonia.advent.readList
import kotlin.math.ceil
import kotlin.math.floor

data class Num(val n: Int, val level: Int)
data class SnailFish(val values: List<Num>) {
    operator fun plus(other: SnailFish) =
        SnailFish(values.map { Num(it.n, it.level + 1) } + other.values.map { Num(it.n, it.level + 1) })

    private fun explode(): SnailFish? {
        if (values.none { it.level > 4 }) return null
        val idx = values.indexOfFirst { it.level > 4 }
        val pair = values.subList(idx, idx + 2)
        val lhs = values.take(idx).let {
            if (it.isNotEmpty()) {
                it.take(it.size - 1) + Num(it.last().n + pair[0].n, it.last().level)
            } else {
                it
            }
        }
        val rhs = values.drop(idx + 2).let {
            if (it.isNotEmpty()) {
                listOf(Num(it[0].n + pair[1].n, it[0].level)) + it.drop(1)
            } else {
                it
            }
        }
        return SnailFish(lhs + Num(0, pair[0].level - 1) + rhs)
    }

    fun split(): SnailFish? {
        if (values.none { it.n >= 10 }) return null
        val idx = values.indexOfFirst { it.n >= 10 }
        val num = values[idx]
        return SnailFish(
            values.take(idx) + listOf(
                Num(floor(num.n / 2.0).toInt(), num.level + 1),
                Num(ceil(num.n / 2.0).toInt(), num.level + 1)
            ) + values.drop(idx + 1)
        )
    }

    private fun magnitude(lhs: Num, rhs: Num) = Num((3 * lhs.n) + (2 * rhs.n), lhs.level - 1)
    fun magnitude(): Int {
        if (values.size == 1) {
            return values[0].n
        }
        val maxLevel = values.maxOf { it.level }
        val first = values.indexOfFirst { it.level == maxLevel }
        val newFish = SnailFish(values.take(first) + magnitude(values[first], values[first + 1]) + values.drop(first + 2))
        return newFish.magnitude()
    }
    fun reduce(): SnailFish {
        var data: SnailFish = this
        while(true) {
            val exploded = data.explode()
            if (exploded != null) {
                data = exploded
                continue
            }
            val split = data.split()
            if (split != null) {
                data = split
                continue
            }
            break
        }
        return data
    }
    override fun toString() = values.joinToString(",") { "${it.n}(${it.level})" }
}

fun String.toSnailFish(): SnailFish {
    var level = 0
    return SnailFish(this
        .replace("[", ".[.")
        .replace("]", ".].")
        .split("[,.]".toPattern())
        .filter { it.isNotEmpty() }
        .mapNotNull {
            when (it) {
                "[" -> {
                    level++
                    null
                }
                "]" -> {
                    level--
                    null
                }
                else -> Num(it.toInt(), level)
            }
        })
}

fun <T, S> Collection<T>.cartesianProduct(other: Iterable<S>): List<Pair<T, S>> {
    return cartesianProduct(other) { first, second -> first to second }
}

fun <T, S, V> Collection<T>.cartesianProduct(other: Iterable<S>, transformer: (first: T, second: S) -> V): List<V> {
    return this.flatMap { first -> other.map { second -> transformer.invoke(first, second) } }
}

fun main() {
    val input = readList("/day18input.txt") { it.toSnailFish() }
    println(input.reduce { a, b -> (a + b).reduce() }.magnitude())
    println(input.cartesianProduct(input).map { (a, b) -> (a + b).reduce().magnitude() }.maxOf { it })
}