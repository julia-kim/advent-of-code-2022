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
        }
        println(visitedPoints.distinctBy { it.x to it.y })
        return visitedPoints.distinctBy { it.x to it.y }.size
    }

    fun part2(input: List<String>): Int {
        val head = Point(0, 0)
        val tail1 = Point(0, 0)
        val tail2 = Point(0, 0)
        val tail3 = Point(0, 0)
        val tail4 = Point(0, 0)
        val tail5 = Point(0, 0)
        val tail6 = Point(0, 0)
        val tail7 = Point(0, 0)
        val tail8 = Point(0, 0)
        val tail9 = Point(0, 0)
        val visitedPoints: MutableList<Point> = mutableListOf(tail9)
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
                val newTail1 = moveTail(head.x, head.y, tail1.copy())
                tail1.x = newTail1.x
                tail1.y = newTail1.y

                val newTail2 = moveTail(tail1.copy().x, tail1.copy().y, tail2.copy())
                tail2.x = newTail2.x
                tail2.y = newTail2.y

                val newTail3 = moveTail(tail2.copy().x, tail2.copy().y, tail3.copy())
                tail3.x = newTail3.x
                tail3.y = newTail3.y

                val newTail4 = moveTail(tail3.copy().x, tail3.copy().y, tail4.copy())
                tail4.x = newTail4.x
                tail4.y = newTail4.y

                val newTail5 = moveTail(tail4.copy().x, tail4.copy().y, tail5.copy())
                tail5.x = newTail5.x
                tail5.y = newTail5.y

                val newTail6 = moveTail(tail5.copy().x, tail5.copy().y, tail6.copy())
                tail6.x = newTail6.x
                tail6.y = newTail6.y

                val newTail7 = moveTail(tail6.copy().x, tail6.copy().y, tail7.copy())
                tail7.x = newTail7.x
                tail7.y = newTail7.y

                val newTail8 = moveTail(tail7.copy().x, tail7.copy().y, tail8.copy())
                tail8.x = newTail8.x
                tail8.y = newTail8.y

                val newTail9 = moveTail(tail8.copy().x, tail8.copy().y, tail9.copy())
                tail9.x = newTail9.x
                tail9.y = newTail9.y

                visitedPoints.add(newTail9)
                steps--
            }
        }
        val visited = visitedPoints.distinctBy { it.x to it.y }
        return visited.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

data class Point(var x: Int, var y: Int)