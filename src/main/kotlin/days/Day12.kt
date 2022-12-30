package days

import Point
import readInput
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        /* Dijkstra's algorithm */
        val heightmap = input.flatMapIndexed { i, string ->
            string.mapIndexed { j, char -> Point(j, i) to char }
        }.toMap().toMutableMap()
        val s = heightmap.entries.find { it.value == 'S' }!!.key
        val target = heightmap.entries.find { it.value == 'E' }!!.key
        heightmap[s] = 'a'
        heightmap[target] = 'z'
        val distances = heightmap.map { it.key to Int.MAX_VALUE }.toMap().toMutableMap()
        distances[s] = 0
        val unvisited = distances.keys.toMutableList()
        while (unvisited.isNotEmpty()) {
            val current = distances.filter { unvisited.contains(it.key) }.minBy { it.value }
            val neighbors =
                distances.filter { ((it.key.x - current.key.x).absoluteValue == 1 && it.key.y == current.key.y) || ((it.key.y - current.key.y).absoluteValue == 1 && it.key.x == current.key.x) }
                    .filter {
                        var currentElevation = heightmap[current.key]!!
                        var neighborElevation = heightmap[it.key]!!
                        if (heightmap[current.key]!! == 'S') currentElevation = 'a'
                        if (heightmap[it.key]!! == 'S') neighborElevation = 'a'
                        if (heightmap[current.key]!! == 'E') currentElevation = 'z'
                        if (heightmap[it.key]!! == 'E') currentElevation = 'z'
                        neighborElevation <= currentElevation + 1
                    }
                    .toMutableMap()
            neighbors.remove(current.key)
            neighbors.forEach { neighbor ->
                if (distances[neighbor.key]!! > distances[current.key]!! + 1) {
                    distances[neighbor.key] = distances[current.key]!! + 1
                }
            }
            if (current.key == target) {
                return distances[target]!!
            }
            unvisited.remove(current.key)
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}