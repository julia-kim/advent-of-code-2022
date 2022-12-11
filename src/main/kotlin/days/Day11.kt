package days

import readInput

fun main() {
    fun parseMonkeys(input: List<String>) = input.chunked(7) {
        val monkey = Monkey()
        it.forEach { string ->
            val line = string.trim()
            when {
                line.startsWith("Monkey") -> return@forEach
                line.startsWith("Starting items") -> {
                    val stringList = line.removePrefix("Starting items: ")
                    monkey.items =
                        if (line.contains(","))
                            stringList.split(", ")
                                .map { it.toLong() }.toMutableList()
                        else mutableListOf(stringList.toLong())
                }

                line.startsWith("Operation") -> {
                    monkey.operation = line.removePrefix("Operation: new = ")
                }

                line.startsWith("Test") -> {
                    monkey.testDivisor = line.removePrefix("Test: divisible by ").toLong()
                }

                line.startsWith("If true") -> {
                    monkey.trueMonkey = line.removePrefix("If true: throw to monkey ").toInt()
                }

                line.startsWith("If false") -> {
                    monkey.falseMonkey = line.removePrefix("If false: throw to monkey ").toInt()
                }

                line.isBlank() -> return@forEach
            }
        }
        monkey
    }

    fun part1(input: List<String>): Long {
        val monkeys = parseMonkeys(input)
        var round = 1
        while (round <= 20) {
            monkeys.forEach { m ->
                val iterator = m.items.iterator()
                while (iterator.hasNext()) {
                    val itemLevel = iterator.next()
                    val worryLevel = m.performOperation(itemLevel, true)
                    val throwToMonkey = m.test(worryLevel)
                    monkeys[throwToMonkey].items.add(worryLevel)
                    iterator.remove()
                    m.inspectionCount++
                }
            }
            round++
        }
        val mostActiveMonkeys = monkeys.sortedByDescending { it.inspectionCount }.map { it.inspectionCount }.take(2)
        return mostActiveMonkeys[0] * mostActiveMonkeys[1] //monkey business
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

data class Monkey(
    var items: MutableList<Long> = mutableListOf(),
    var operation: String = "",
    var testDivisor: Long = 1L,
    var trueMonkey: Int = 0,
    var falseMonkey: Int = 0,
    var inspectionCount: Long = 0
) {
    fun performOperation(old: Long, decreaseWorryLevel: Boolean): Long {
        val (n1, operator, n2) = operation.split(" ")
        val value1 = if (n1 == "old") old else n1.toLong()
        val value2 = if (n2 == "old") old else n2.toLong()
        val worryLevel: Long = when (operator) {
            "*" -> value1 * value2
            "+" -> value1 + value2
            else -> 0L
        }
        val divideBy3 = (worryLevel / 3L)
        return if (decreaseWorryLevel) divideBy3 else worryLevel
    }

    fun test(wl: Long): Int {
        return if (wl % testDivisor == 0L) trueMonkey else falseMonkey
    }
}