package days

import Point
import readInput

fun main() {
    fun printCave(visited: List<Point>, sand: List<Point>) {
        var rows = visited.minOf { it.y }
        while (rows <= visited.maxOf { it.y }) {
            var cols = visited.minOf { it.x }
            while (cols <= visited.maxOf { it.x }) {
                when {
                    (rows == 500 && cols == 0) -> print("+")
                    (sand.contains(Point(cols, rows))) -> print("o")
                    (visited.contains(Point(cols, rows))) -> print("#")
                    else -> print(".")
                }
                cols++
            }
            println()
            rows++
        }
    }

    fun fallingSand(sand: Point, cave: List<Point>, restingSand: MutableList<Point>): Boolean {
        val oneBelow = Point(sand.x, sand.y + 1)
        val leftBelow = Point(sand.x - 1, sand.y + 1)
        val rightBelow = Point(sand.x + 1, sand.y + 1)
        when {
            !cave.contains(oneBelow) && !restingSand.contains(oneBelow) ->
                fallingSand(oneBelow, cave, restingSand)

            !cave.contains(leftBelow) && !restingSand.contains(leftBelow) ->
                fallingSand(leftBelow, cave, restingSand)

            !cave.contains(rightBelow) && !restingSand.contains(rightBelow) ->
                fallingSand(rightBelow, cave, restingSand)

            else -> restingSand.add(sand)
        }
        return true
    }


    fun part1(input: List<String>): Long {
        var sandCount = 0L
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
                    if (dx < 0) cave.add(Point(firstPoint.x--, firstPoint.y)) else
                        cave.add(Point(firstPoint.x++, firstPoint.y))
                    dx = secondPoint.x - firstPoint.x
                }
                while (dy != 0) {
                    if (dy < 0) cave.add(Point(firstPoint.x, firstPoint.y--)) else
                        cave.add(Point(firstPoint.x, firstPoint.y++))
                    cave.add(firstPoint)
                    dy = secondPoint.y - firstPoint.y
                }
            }
        }
        val restingSand = mutableListOf<Point>()
        while (sandCount < Int.MAX_VALUE) {
            val sand = Point(500, 0)
            if (fallingSand(sand, cave, restingSand)) sandCount++
            println(sandCount)
        }
        printCave(cave.distinctBy { it.x to it.y }, restingSand.distinctBy { it.x to it.y })
        return sandCount
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    val testInput = readInput("Day14_test")
    //  check(part1(testInput) == 0L)
    check(part2(testInput) == 0)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}