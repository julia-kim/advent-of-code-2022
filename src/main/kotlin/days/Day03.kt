package days

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        val lettersToNumber: MutableMap<Char, Int> = mutableMapOf()
        ('a'..'z').forEachIndexed { i, c ->
            lettersToNumber[c] = i + 1
        }
        ('A'..'Z').forEachIndexed { i, c ->
            lettersToNumber[c] = i + 27
        }
        input.forEach { rucksack ->
            val halfLength = rucksack.length / 2
            val compartment1 = rucksack.take(halfLength).map { it }
            val compartment2 = rucksack.takeLast(halfLength).map { it }
            val common = compartment1.first { compartment2.contains(it) }
            sum += lettersToNumber[common] ?: 0
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}