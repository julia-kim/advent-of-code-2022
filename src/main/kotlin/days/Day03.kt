package days

import readInput

fun main() {
    val lettersToNumber = (('a'..'z') + ('A'..'Z')).mapIndexed { i, c ->
        c to (i + 1)
    }.toMap()

    fun part1(input: List<String>): Int {
        var sum = 0
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
        var sum = 0
        val allRucksacks = input.map { it.map { it } }
        allRucksacks.chunked(3).forEach { group ->
            val common = group[0].first { itemType ->
                group[1].contains(itemType) && group[2].contains(itemType)
            }
            sum += lettersToNumber[common] ?: 0
        }
        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}