package days

import readInput
import kotlin.math.absoluteValue

fun main() {
    fun moveTail(x: Int, y: Int, tail: Point): Point {
        val xDifference = x - tail.x
        val yDifference = y - tail.y
        if (xDifference.absoluteValue <= 1 && yDifference.absoluteValue <= 1) return tail
        var newX = tail.x
        var newY = tail.y
        when {
            (xDifference == 2 && yDifference == 1) -> {
                newX += 1
                newY += 1
            }

            (xDifference == 2 && yDifference == -1) -> {
                newX += 1
                newY -= 1
            }

            (xDifference == -2 && yDifference == -1) -> {
                newX -= 1
                newY -= 1
            }

            (xDifference == -2 && yDifference == 1) -> {
                newX -= 1
                newY += 1
            }

            (xDifference == 1 && yDifference == 2) -> {
                newX += 1
                newY += 1
            }

            (xDifference == 1 && yDifference == -2) -> {
                newX += 1
                newY -= 1
            }

            (xDifference == -1 && yDifference == -2) -> {
                newX -= 1
                newY -= 1
            }

            (xDifference == -1 && yDifference == 2) -> {
                newX -= 1
                newY += 1
            }

            (xDifference == -2) -> {
                newX -= 1
            }

            (xDifference == 2) -> {
                newX += 1
            }

            (yDifference == 2) -> {
                newY += 1
            }

            (yDifference == -2) -> {
                newY -= 1
            }
        }
        return Point(newX, newY)
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
            println(head)
        }
        println(visitedPoints.distinctBy { it.x to it.y })
        return visitedPoints.distinctBy { it.x to it.y }.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 0)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

data class Point(var x: Int, var y: Int)