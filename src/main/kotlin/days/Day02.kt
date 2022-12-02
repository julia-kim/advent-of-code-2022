package days

import readInput

fun main() {
    // opponent plays - A for Rock, B for Paper, and C for Scissors
    // you play - X for Rock, Y for Paper, and Z for Scissors
    // total score: add up shape selected + outcome of the round score
    //              1 for Rock, 2 for Paper, and 3 for Scissors
    //              0 if you lost, 3 if the round was a draw, and 6 if you won
    fun part1(input: List<String>): Int {
        var totalScore = 0
        // even is win, odd is draw
        val moves = listOf("A Y", "A X", "B Z", "B Y", "C X", "C Z")
        input.forEach { strategy ->
            if (moves.contains(strategy)) {
                if (moves.indexOf(strategy) % 2 == 0) {
                    totalScore += 6
                } else totalScore += 3
            }
            val (_, myMove) = strategy.split(" ")
            totalScore += when (myMove) {
                "X" -> 1
                "Y" -> 2
                "Z" -> 3
                else -> 0
            }

        }
        return totalScore
    }

    fun part2(input: List<String>): Int {
        var totalScore = 0
        // even is win, odd is loss
        val moves = listOf("A B", "A C", "B C", "B A", "C A", "C B")
        val myMoves = input.map { strategy ->
            val (opponentsMove, outcome) = strategy.split(" ")
            when (outcome) { // X means lose, Y means draw, and Z means win
                "X" -> moves.last { it.take(1) == opponentsMove }.takeLast(1)
                "Y" -> {
                    totalScore += 3
                    opponentsMove
                }

                "Z" -> {
                    totalScore += 6
                    moves.first { it.take(1) == opponentsMove }.takeLast(1)
                }

                else -> ""
            }
        }
        myMoves.forEach {
            totalScore += when (it) {
                "A" -> 1
                "B" -> 2
                "C" -> 3
                else -> 0
            }
        }
        return totalScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
