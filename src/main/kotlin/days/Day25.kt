package days

import readInput
import kotlin.math.absoluteValue
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    fun Long.toSNAFU(): String {
        var snafu = ""
        var decimal = this.toDouble()
        var currentPlace = log(this.toDouble(), 5.0).roundToInt()
        while (currentPlace >= 0) {
            val currentPlaceValue = 5.0.pow(currentPlace)
            when ((decimal / currentPlaceValue).roundToInt()) {
                1 -> {
                    snafu += "1"
                    decimal -= currentPlaceValue
                }

                2 -> {
                    snafu += "2"
                    decimal -= currentPlaceValue * 2
                }

                -1 -> {
                    snafu += "-"
                    decimal += currentPlaceValue
                }

                -2 -> {
                    snafu += "="
                    decimal += currentPlaceValue * 2
                }

                else -> {
                    snafu += "0"
                }
            }

            currentPlace--
        }
        return snafu
    }

    fun part1(input: List<String>): String {
        val decimalSum = input.map { snafu ->
            snafu.mapIndexed { i, c ->
                val nthPlace = 5.0.pow((i - snafu.lastIndex).absoluteValue).toLong()
                when (c) {
                    '2' -> 2 * nthPlace
                    '1' -> nthPlace
                    '0' -> 0
                    '-' -> -nthPlace
                    '=' -> -2 * nthPlace
                    else -> {
                        0
                    }
                }
            }.sum()
        }.sum()
        return decimalSum.toSNAFU()
    }

    val testInput = readInput("Day25_test")
    check(part1(testInput) == "2=-1=0")

    val input = readInput("Day25")
    println(part1(input))
}