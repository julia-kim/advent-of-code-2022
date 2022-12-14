package days

import Point
import readInput
import kotlin.math.absoluteValue

fun main() {
    fun printTail(visited: List<Point>) {
        var rows = visited.maxOf { it.y }
        while (rows >= visited.minOf { it.y }) {
            var cols = visited.minOf { it.x }
            while (cols <= visited.maxOf { it.x }) {
                if (visited.contains(Point(cols, rows))) {
                    if (rows == 0 && cols == 0) {
                        print("s")
                    } else print("#")
                } else print(".")
                cols++
            }
            println()
            rows--
        }
    }

    fun moveTail(x: Int, y: Int, tail: Point): Point {
        val dx = (x - tail.x)
        val dy = (y - tail.y)
        if (dx.absoluteValue <= 1 && dy.absoluteValue <= 1) return tail
        when {
            (dx == 2 && dy >= 1) || (dx >= 1 && dy == 2) -> {
                tail.x += 1
                tail.y += 1
            }

            (dx == 2 && dy <= -1) || (dx >= 1 && dy == -2) -> {
                tail.x += 1
                tail.y -= 1
            }

            (dx == -2 && dy <= -1) || (dx <= -1 && dy == -2) -> {
                tail.x -= 1
                tail.y -= 1
            }

            (dx == -2 && dy >= 1) || (dx <= -1 && dy == 2) -> {
                tail.x -= 1
                tail.y += 1
            }

            (dx == -2) -> {
                tail.x -= 1
            }

            (dx == 2) -> {
                tail.x += 1
            }

            (dy == 2) -> {
                tail.y += 1
            }

            (dy == -2) -> {
                tail.y -= 1
            }
        }
        return Point(tail.x, tail.y)
    }

    fun part1(input: List<String>): Int {
        val head = Point(0, 0)
        val tail = Point(0, 0)
        val visitedPoints: MutableList<Point> = mutableListOf(tail)
        input.forEach {
            val direction = it.take(1)
            var steps = it.split(" ").last().toInt()
            while (steps > 0) {
                when (direction) {
                    "R" -> head.x += 1
                    "L" -> head.x -= 1
                    "U" -> head.y += 1
                    "D" -> head.y -= 1
                }
                val newTail = moveTail(head.x, head.y, tail.copy())
                tail.x = newTail.x
                tail.y = newTail.y
                visitedPoints.add(newTail)
                steps--
            }
        }
        return visitedPoints.distinctBy { it.x to it.y }.size
    }

    fun part2(input: List<String>): Int {
        val rope = (0 until 10).map { Point(0, 0) }.toMutableList()
        val visitedPoints = mutableListOf(rope[9])
        input.forEach {
            val direction = it.take(1)
            var steps = it.split(" ").last().toInt()
            while (steps > 0) {
                when (direction) {
                    "R" -> rope[0].x += 1
                    "L" -> rope[0].x -= 1
                    "U" -> rope[0].y += 1
                    "D" -> rope[0].y -= 1
                }
                (1..9).forEach { i ->
                    val tail = moveTail(rope[i - 1].x, rope[i - 1].y, rope[i].copy())
                    rope[i] = tail
                }
                visitedPoints.add(rope[9])
                steps--
            }
        }
        val visited = visitedPoints.distinctBy { it.x to it.y }
        printTail(visited)
        return visited.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 88)
    check(part2(testInput) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}