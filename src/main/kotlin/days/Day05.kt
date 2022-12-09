package days

import readInput

fun main() {
    fun makeStacks(input: List<String>): MutableMap<Int?, MutableList<Char>> {
        val blankLineIndex = input.indexOfFirst { it.isBlank() }
        val stacksIndices =
            input[blankLineIndex - 1].mapIndexedNotNull { i, c -> if (c.isDigit()) i to c.digitToInt() else null }
                .toMap()
        val stacks: MutableMap<Int?, MutableList<Char>> = mutableMapOf()
        input.subList(0, blankLineIndex - 1).reversed().forEach {
            it.forEachIndexed { i, c ->
                if (c.isLetter()) {
                    val matchingIndex = stacksIndices[i]
                    if (stacks[matchingIndex].isNullOrEmpty()) stacks[matchingIndex] =
                        mutableListOf(c) else stacks[matchingIndex]!!.add(c)
                }
            }
        }
        return stacks
    }

    fun parseInstructions(input: List<String>): List<Triple<Int, Int, Int>> {
        val blankLineIndex = input.indexOfFirst { it.isBlank() }
        val rearrangementProcedure = input.subList(blankLineIndex + 1, input.size)
        return rearrangementProcedure.map {
            val (qty, from, to) = it.split(" ").mapNotNull {
                if (it.toIntOrNull() != null) it.toInt() else
                    null
            }
            Triple(qty, from, to)
        }
    }

    fun part1(input: List<String>): String {
        var message = ""
        val stacks = makeStacks(input)
        val instructions = parseInstructions(input)
        instructions.forEach {
            var (qty, from, to) = it
            while (qty > 0) {
                val crate = stacks[from]!!.removeLast()
                stacks[to]!!.add(crate)
                qty--
            }
        }
        stacks.forEach { message += it.value.last() }
        return message
    }

    fun part2(input: List<String>): String {
        val message: String
        val stacks = makeStacks(input)
        val instructions = parseInstructions(input)
        instructions.forEach {
            val (qty, from, to) = it
            val crates = stacks[from]!!.takeLast(qty)
            stacks[from] = stacks[from]!!.dropLast(qty).toMutableList()
            stacks[to]!!.addAll(crates)
        }
        message = stacks.values.joinToString(separator = "") { it.last().toString() }
        return message
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}