package days

import readInput

fun main() {
    fun mapElvesToCaloriesCounted(input: List<String>): HashMap<Int, Int> {
        var i = 1
        val hm: HashMap<Int, Int> = hashMapOf()
        input.forEach { calories ->
            if (calories.isBlank()) {
                i++
                return@forEach
            }
            hm[i] = (hm[i] ?: 0) + calories.toInt()
        }
        return hm
    }

    fun part1(input: List<String>): Int {
        return mapElvesToCaloriesCounted(input).values.max()
    }

    fun part2(input: List<String>): Int {
        return mapElvesToCaloriesCounted(input).values.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
