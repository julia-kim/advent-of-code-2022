package days

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val monkeyNumbers = mutableMapOf<String, Long>()
        val monkeyOperations = mutableMapOf<String, String>()
        input.forEach {
            val (name, job) = it.split(": ")
            if (job.all { it.isDigit() }) monkeyNumbers[name] = job.toLong()
            else monkeyOperations[name] = job
        }
        while (!monkeyNumbers.containsKey("root")) {
            monkeyOperations.forEach {
                val (name, job) = it
                if (!monkeyNumbers.containsKey(name)) {
                    val monkey1 = job.take(4)
                    val monkey2 = job.takeLast(4)
                    val mathOperator = job[5]
                    if (monkeyNumbers.containsKey(monkey1) && monkeyNumbers.containsKey(monkey2)) {
                        monkeyNumbers[name] = when (mathOperator) {
                            '+' -> monkeyNumbers[monkey1]!! + monkeyNumbers[monkey2]!!
                            '-' -> monkeyNumbers[monkey1]!! - monkeyNumbers[monkey2]!!
                            '*' -> monkeyNumbers[monkey1]!! * monkeyNumbers[monkey2]!!
                            '/' -> monkeyNumbers[monkey1]!! / monkeyNumbers[monkey2]!!
                            else -> {
                                throw IllegalStateException()
                            }
                        }
                    }
                }
            }
        }
        return monkeyNumbers["root"]!!
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    val testInput = readInput("Day21_test")
    check(part1(testInput) == 152L)
    check(part2(testInput) == 0)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}