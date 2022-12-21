package days

import readInput
import kotlin.math.absoluteValue

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

    fun part2(input: List<String>): Long {
        val monkeyNumbers = mutableMapOf<String, Long>()
        val monkeyOperations = mutableMapOf<String, String>()
        input.forEach {
            val (name, job) = it.split(": ")
            if (name == "humn") return@forEach
            if (job.all { it.isDigit() }) monkeyNumbers[name] = job.toLong()
            else monkeyOperations[name] = job
        }
        val monkeyRoot = mutableListOf(monkeyOperations["root"]!!.take(4), monkeyOperations["root"]!!.takeLast(4))
        monkeyOperations.remove("root")
        var magicNumber: Long = 0
        while (magicNumber == 0L) {
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
                        if (monkeyRoot.contains(name)) magicNumber = monkeyNumbers[name]!!
                        monkeyRoot.remove(name)
                    }
                }
            }
        }
        monkeyNumbers.keys.forEach {
            monkeyOperations.remove(it)
        }
        monkeyNumbers[monkeyRoot[0]] = magicNumber
        while (!monkeyNumbers.containsKey("humn")) {
            monkeyOperations.forEach {
                val (name, job) = it
                val monkey1 = job.take(4)
                val monkey2 = job.takeLast(4)
                val mathOperator = job[5]
                when {
                    monkeyNumbers.containsKey(name) && monkeyNumbers.containsKey(monkey1) -> {
                        monkeyNumbers[monkey2] = when (mathOperator) {
                            '+' -> monkeyNumbers[name]!! - monkeyNumbers[monkey1]!!
                            '-' -> (monkeyNumbers[name]!! - monkeyNumbers[monkey1]!!).absoluteValue
                            '*' -> monkeyNumbers[name]!! / monkeyNumbers[monkey1]!!
                            '/' -> monkeyNumbers[name]!! * (1 / monkeyNumbers[monkey1]!!)
                            else -> {
                                throw IllegalStateException()
                            }
                        }
                    }

                    monkeyNumbers.containsKey(name) && monkeyNumbers.containsKey(monkey2) -> {
                        monkeyNumbers[monkey1] = when (mathOperator) {
                            '+' -> monkeyNumbers[name]!! - monkeyNumbers[monkey2]!!
                            '-' -> monkeyNumbers[name]!! + monkeyNumbers[monkey2]!!
                            '*' -> monkeyNumbers[name]!! / monkeyNumbers[monkey2]!!
                            '/' -> monkeyNumbers[name]!! * monkeyNumbers[monkey2]!!
                            else -> {
                                throw IllegalStateException()
                            }
                        }
                    }
                }
            }
        }
        return monkeyNumbers["humn"]!!
    }

    val testInput = readInput("Day21_test")
    check(part1(testInput) == 152L)
    check(part2(testInput) == 301L)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}