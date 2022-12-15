package days

import Point
import readInput

fun main() {
    fun getRockCoordinates(input: List<String>): List<Point> {
        val pourPoint = Point(500, 0)
        val rocks = mutableListOf(pourPoint)
        input.forEach { line ->
            // 498,4 -> 498,6 -> 496,6
            val paths = line.split(" -> ")
                .map { point -> point.split(",").let { Point(it.first().toInt(), it.last().toInt()) } }
            rocks.addAll(paths)
            paths.windowed(2).forEach {
                val (firstPoint, secondPoint) = it.map { coords -> coords.copy() }
                var dx = secondPoint.x - firstPoint.x
                var dy = secondPoint.y - firstPoint.y
                while (dx != 0) {
                    if (dx < 0) rocks.add(Point(firstPoint.x--, firstPoint.y))
                    else rocks.add(Point(firstPoint.x++, firstPoint.y))
                    dx = secondPoint.x - firstPoint.x
                }
                while (dy != 0) {
                    if (dy < 0) rocks.add(Point(firstPoint.x, firstPoint.y--))
                    else rocks.add(Point(firstPoint.x, firstPoint.y++))
                    dy = secondPoint.y - firstPoint.y
                }
            }
        }
        return rocks.distinctBy { it.x to it.y }
    }

    fun visualizeCave(rocks: List<Point>, sand: List<Point>) {
        var rows = rocks.minOf { it.y }
        while (rows <= rocks.maxOf { it.y } + 2) {
            var cols = minOf(rocks.minOf { it.x }, sand.minOf { it.x })
            while (cols <= maxOf(rocks.maxOf { it.x }, sand.maxOf { it.x })) {
                when {
                    (rows == 500 && cols == 0) -> print("+")
                    (sand.contains(Point(cols, rows))) -> print("o")
                    (rocks.contains(Point(cols, rows))) -> print("#")
                    else -> print(".")
                }
                cols++
            }
            println()
            rows++
        }
    }

    fun dropSand(grain: Point, cave: MutableList<Point>, sand: MutableList<Point>, caveBottom: Int? = null): Boolean {
        cave.addAll(sand)
        val oneBelow = Point(grain.x, grain.y + 1)
        val leftBelow = Point(grain.x - 1, grain.y + 1)
        val rightBelow = Point(grain.x + 1, grain.y + 1)

        when {
            (oneBelow.y == caveBottom) -> {
                sand.add(grain)
                return true
            }

            !cave.contains(oneBelow) -> dropSand(oneBelow, cave, sand, caveBottom)
            !cave.contains(leftBelow) -> dropSand(leftBelow, cave, sand, caveBottom)
            !cave.contains(rightBelow) -> dropSand(rightBelow, cave, sand, caveBottom)
            else -> {
                sand.add(grain); return true
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val rocks = getRockCoordinates(input)
        val restingSand = mutableListOf<Point>()
        var sandCount = 0
        try {
            while (sandCount < Int.MAX_VALUE) {
                if (dropSand(Point(500, 0), rocks.toMutableList(), restingSand)) sandCount++
            }
        } catch (e: StackOverflowError) {
            visualizeCave(rocks, restingSand)
            return sandCount
        }
        return sandCount
    }

    fun part2(input: List<String>): Int {
        val rocks = getRockCoordinates(input)
        val restingSand = mutableListOf<Point>()
        val caveBottom = rocks.maxOf { it.y } + 2
        val pourPoint = Point(500, 0)
        while (!restingSand.contains(pourPoint)) {
            dropSand(pourPoint, rocks.toMutableList(), restingSand, caveBottom)
        }
        visualizeCave(rocks, restingSand)
        return restingSand.size
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}