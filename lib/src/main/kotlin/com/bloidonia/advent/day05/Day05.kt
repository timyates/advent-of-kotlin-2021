package com.bloidonia.advent.day05

import com.bloidonia.advent.readList
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Math.abs
import javax.imageio.ImageIO

data class Point(val x: Int, val y: Int) {
    override fun toString(): String = "($x,$y)"
}

data class Vent(val start: Point, val end: Point) {
    fun notDiagonal() = start.x == end.x || start.y == end.y

    // bresenham line algorithm
    fun coveredPoints(): List<Point> {
        var d = 0
        val result = mutableListOf<Point>()
        val dx = abs(start.x - end.x)
        val dy = abs(start.y - end.y)
        val dirX = if (start.x < end.x) 1 else -1
        val dirY = if (start.y < end.y) 1 else -1
        var x = start.x
        var y = start.y
        if (dx > dy) {
            while (true) {
                result.add(Point(x, y))
                if (x == end.x) break
                x += dirX
                d += 2 * dy;
                if (d > dx) {
                    y += dirY;
                    d -= 2 * dy;
                }
            }
        } else {
            while (true) {
                result.add(Point(x, y))
                if (y == end.y) break
                y += dirY
                d += 2 * dx;
                if (d > dy) {
                    x += dirX;
                    d -= 2 * dx;
                }
            }
        }
        return result
    }

    override fun toString(): String = "$start -> $end"
}

fun String.toPoint() = split(",", limit = 2).let { (x, y) -> Point(x.toInt(), y.toInt()) }
fun String.toVent() = split(" -> ".toPattern(), limit = 2).let { (start, end) -> Vent(start.toPoint(), end.toPoint()) }

fun coveragePart1(vents: List<Vent>): Int = coveragePart2(vents.filter { it.notDiagonal() })

fun coveragePart2(vents: List<Vent>): Int =
    vents.flatMap { it.coveredPoints() }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .count()

fun drawForFun(vents: List<Vent>) {
    val points = vents.flatMap { listOf(it.start, it.end) }
    val maxX = points.maxOf { it.x }
    val maxY = points.maxOf { it.y }
    val buffer = BufferedImage(maxX + 1, maxY + 1, BufferedImage.TYPE_INT_ARGB)
    buffer.graphics.apply {
        color = Color.WHITE
        fillRect(0, 0, maxX + 1, maxY + 1)
        color = Color.BLACK
        vents.forEach {
            drawLine(it.start.x, it.start.y, it.end.x, it.end.y)
        }
        ImageIO.write(buffer, "png", File("/tmp/vents.png"))
    }
}

fun main() {
    println(coveragePart1(readList("/day05input.txt") { it.toVent() }))
    println(coveragePart2(readList("/day05input.txt") { it.toVent() }))
    drawForFun(readList("/day05input.txt") { it.toVent() })
}
