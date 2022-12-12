package days

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        // cycleNumber to register
        val signals: MutableMap<Int, Int> = mutableMapOf()
        var cycleNumber = 0
        var x = 1
        // Find the signal strength during the 20th, 60th, 100th, 140th, 180th, and 220th cycles.
        val signalStrengths = mutableListOf<Int>()
        input.forEach {
            val currentX = x
            if (it == "noop") {
                cycleNumber++
            } else {
                cycleNumber += 2
                x += it.removePrefix("addx ").toInt()
            }
            signals[cycleNumber] = x
            listOf(20, 60, 100, 140, 180, 220).forEach { i ->
                if (cycleNumber == i) signalStrengths.add(i * currentX)
                if (cycleNumber > i && !signals.contains(i)) {
                    signals[i] = currentX
                    signalStrengths.add(i * currentX)
                }
            }
        }
        return signalStrengths.sum()
    }

    fun part2(input: List<String>) {
        var x = 1
        val iterator = input.iterator()
        var rows = 0
        var addV = false
        var op = iterator.next()
        while (rows < 6) {
            var cols = 0
            while (cols < 40) {
                if (cols in (x - 1..x + 1))
                    print("#") else print(".")
                if (op == "noop") {
                    op = if (iterator.hasNext()) iterator.next() else return
                    cols++
                } else {
                    if (!addV) {
                        addV = true
                    } else {
                        x += op.removePrefix("addx ").toInt()
                        addV = false
                        op = if (iterator.hasNext()) iterator.next() else return
                    }
                    cols++
                }
            }
            println()
            rows++
        }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)
    println()

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}