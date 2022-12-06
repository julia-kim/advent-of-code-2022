package days

import readInput

fun main() {
    fun part1(input: List<String>): String {
        var message = ""
//        val stacks = mapOf(
//            1 to ArrayDeque(listOf('Z', 'N')),
//            2 to ArrayDeque(listOf('M', 'C', 'D')),
//            3 to ArrayDeque(listOf('P'))
//        )
        val stacks = mapOf(
            1 to ArrayDeque(listOf('D', 'M', 'S', 'Z', 'R', 'F', 'W', 'N')),
            2 to ArrayDeque(listOf('W', 'P', 'Q', 'G', 'S')),
            3 to ArrayDeque(listOf('W', 'R', 'V', 'Q', 'F', 'N', 'J', 'C')),
            4 to ArrayDeque(listOf('F', 'Z', 'P', 'C', 'G', 'D', 'L')),
            5 to ArrayDeque(listOf('T', 'P', 'S')),
            6 to ArrayDeque(listOf('H', 'D', 'F', 'W', 'R', 'L')),
            7 to ArrayDeque(listOf('Z', 'N', 'D', 'C')),
            8 to ArrayDeque(listOf('W', 'N', 'R', 'F', 'V', 'S', 'J', 'Q')),
            9 to ArrayDeque(listOf('R', 'M', 'S', 'G', 'Z', 'W', 'V')),

            )
        val rearrangementProcedure = input.subList(10, input.size)
        rearrangementProcedure.forEach {
            var (qty, from, to) = it.split(" ").mapNotNull { if (it.toIntOrNull() != null) it.toInt() else null }
            while (qty > 0) {
                val crate = stacks[from]!!.removeLast()
                stacks[to]!!.add(crate)
                qty--
            }
        }
        println(stacks)
        stacks.forEach { message += it.value.removeLast() }
        return message
    }

    fun part2(input: List<String>): String {
        var message = ""
        val stacks = mutableMapOf(
            1 to mutableListOf('D', 'M', 'S', 'Z', 'R', 'F', 'W', 'N'),
            2 to mutableListOf('W', 'P', 'Q', 'G', 'S'),
            3 to mutableListOf('W', 'R', 'V', 'Q', 'F', 'N', 'J', 'C'),
            4 to mutableListOf('F', 'Z', 'P', 'C', 'G', 'D', 'L'),
            5 to mutableListOf('T', 'P', 'S'),
            6 to mutableListOf('H', 'D', 'F', 'W', 'R', 'L'),
            7 to mutableListOf('Z', 'N', 'D', 'C'),
            8 to mutableListOf('W', 'N', 'R', 'F', 'V', 'S', 'J', 'Q'),
            9 to mutableListOf('R', 'M', 'S', 'G', 'Z', 'W', 'V'),

            )
        val rearrangementProcedure = input.subList(10, input.size)
        rearrangementProcedure.forEach {
            val (qty, from, to) = it.split(" ").mapNotNull { if (it.toIntOrNull() != null) it.toInt() else null }
            val crates = stacks[from]!!.takeLast(qty)
            println(crates)
            stacks[from] = stacks[from]!!.dropLast(qty).toMutableList()
            stacks[to]!!.addAll(crates)
        }
        println(stacks)
        stacks.forEach { message += it.value.last() }
        return message
    }

    val testInput = readInput("Day05_test")
    //  check(part1(testInput) == "CMZ")
    //  check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}