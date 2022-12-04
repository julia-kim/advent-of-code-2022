package days

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var assignmentPairs = 0
        input.forEach {
            // 2-4,6-8
            val (first, second) = it.split(",").map { range ->
                val (start, end) = range.split("-", limit = 2).map(String::toInt)
                start..end
            }
            val combined = (first + second).distinct().sorted()
            if (combined == first.toList() || combined == second.toList()) assignmentPairs++
        }
        return assignmentPairs
    }

    fun part2(input: List<String>): Int {
        var overlappingPairs = 0
        input.forEach {
            val (first, second) = it.split(",").map { range ->
                range.split("-").let { (a, b) -> a.toInt()..b.toInt() }
            }
            if (first.any { second.contains(it) }) overlappingPairs++
        }
        return overlappingPairs
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}