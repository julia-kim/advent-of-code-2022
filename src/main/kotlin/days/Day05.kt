package days

import readInput

fun main() {
    fun part1(input: List<String>): String {
        var message = ""
        val stacks = mapOf(
            1 to ArrayDeque(listOf('Z', 'N')),
            2 to ArrayDeque(listOf('M', 'C', 'D')),
            3 to ArrayDeque(listOf('P'))
        )
        val rearrangementProcedure = input.subList(5, input.size)
        rearrangementProcedure.forEach {
            var (qty, from, to) = it.split(" ").mapNotNull { if (it.toIntOrNull() != null) it.toInt() else null }
            while (qty > 0) {
                val crate =stacks[from]!!.removeLast()
                stacks[to]!!.add(crate)
                qty--
            }
        }
        println(stacks)
        message = "${stacks[1]!!.removeLast()}${stacks[2]!!.removeLast()}${stacks[3]!!.removeLast()}"
        return message
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == 0)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}