package days

import Point
import readInput

fun main() {
    fun printCave(visited: List<Point>) {
        var rows = visited.minOf { it.y }
        while (rows <= visited.maxOf { it.y }) {
            var cols = visited.minOf { it.x }
            while (cols <= visited.maxOf { it.x }) {
                if (visited.contains(Point(cols, rows))) {
                    if (rows == 500 && cols == 0) {
                        print("+")
                    } else print("#")
                } else print(".")
                cols++
            }
            println()
            rows++
        }
    }

    fun part1(input: List<String>): Int {
        var sandCount = 0
        val pourPoint = Point(500, 0)
        val cave = mutableListOf(pourPoint)
        input.forEach { line ->
            // 498,4 -> 498,6 -> 496,6
            val paths = line.split(" -> ")
                .map { point -> point.split(",").let { Point(it.first().toInt(), it.last().toInt()) } }
            cave.addAll(paths)
            paths.windowed(2).forEach {
                val (firstPoint, secondPoint) = it.map { it.copy() }
                var dx = secondPoint.x - firstPoint.x
                var dy = secondPoint.y - firstPoint.y
                while (dx != 0) {
                    if (dx < 0) cave.add(Point(firstPoint.x--, firstPoint.y)) else cave.add(
                        Point(
                            firstPoint.x++,
                            firstPoint.y
                        )
                    )
                    dx = secondPoint.x - firstPoint.x
                }
                while (dy != 0) {
                    if (dy < 0) cave.add(Point(firstPoint.x, firstPoint.y--)) else cave.add(
                        Point(
                            firstPoint.x,
                            firstPoint.y++
                        )
                    )
                    cave.add(firstPoint)
                    dy = secondPoint.y - firstPoint.y
                }
            }
        }
        printCave(cave.distinctBy { it.x to it.y })
        return sandCount
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 0)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}