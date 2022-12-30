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
                        val currentElevation = heightmap[current.key]!!
                        val neighborElevation = heightmap[it.key]!!
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
        /* Dijkstra's algorithm */
        val heightmap = input.flatMapIndexed { i, string ->
            string.mapIndexed { j, char -> Point(j, i) to char }
        }.toMap().toMutableMap()
        val s = heightmap.entries.find { it.value == 'S' }!!.key
        val start = heightmap.entries.find { it.value == 'E' }!!.key
        heightmap[s] = 'a'
        heightmap[start] = 'z'
        val target = heightmap.entries.filter { it.value == 'a' }.map { it.key }
        val distances = heightmap.map { it.key to Int.MAX_VALUE }.toMap().toMutableMap()
        distances[start] = 0
        val unvisited = distances.keys.toMutableList()
        while (unvisited.isNotEmpty()) {
            val current = distances.filter { unvisited.contains(it.key) }.minBy { it.value }
            val neighbors =
                distances.filter { ((it.key.x - current.key.x).absoluteValue == 1 && it.key.y == current.key.y) || ((it.key.y - current.key.y).absoluteValue == 1 && it.key.x == current.key.x) }
                    .filter {
                        val currentElevation = heightmap[current.key]!!
                        val neighborElevation = heightmap[it.key]!!
                        neighborElevation - currentElevation >= -1
                    }
                    .toMutableMap()
            neighbors.remove(current.key)
            neighbors.forEach { neighbor ->
                if (distances[neighbor.key]!! > distances[current.key]!! + 1) {
                    distances[neighbor.key] = distances[current.key]!! + 1
                }
            }
            if (target.contains(current.key)) {
                return distances[current.key]!!
            }
            unvisited.remove(current.key)
        }
        println(distances.filter { target.contains(it.key) })
        return distances.filter { target.contains(it.key) }.values.min()
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}