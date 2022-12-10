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
                    println("$cycleNumber $currentX")
                    signals[i] = currentX
                    signalStrengths.add(i * currentX)
                }
            }
        }
        println(signals)
        return signalStrengths.sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}