package days

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val windows = input[0].windowed(4)
        val sequence = windows.first { it.toList().distinct().size == 4 }
        return input[0].indexOf(sequence) + 4
    }

    fun part2(input: List<String>): Int {
        val windows = input[0].windowed(14)
        val sequence = windows.first { it.toList().distinct().size == 14 }
        return input[0].indexOf(sequence) + 14
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}