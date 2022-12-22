package days

import readInput

fun main() {
    fun parseInput(input: List<String>): Pair<Array<CharArray>, List<String>> {
        val blankLineIndex = input.indexOfFirst { it.isBlank() }
        val board = input.subList(0, blankLineIndex).map {
            it.map { char -> char }.toCharArray()
        }.toTypedArray()
        val paths = input.last().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)".toRegex())
        return board to paths
    }

    fun part1(input: List<String>): Int {
        val (board, paths) = parseInput(input)
        var direction = 90
        var position = board[0].indexOfFirst { !it.isWhitespace() } to 0 //col to row
        paths.forEach { instruction ->
            when (instruction) {
                "R" -> {
                    direction = if (direction == 360) 90 else direction + 90
                }
                "L" -> {
                    direction = if (direction == 0) 270 else direction - 90
                }
                else -> {
                    var number = instruction.toInt()
                    while (number > 0) {
                        val (nextMove, newPosition) = try {
                            when (direction) {
                                90 -> board[position.second][position.first + 1] to (position.first + 1 to position.second)
                                180 -> board[position.second + 1][position.first] to (position.first to position.second + 1)
                                270 -> board[position.second][position.first - 1] to (position.first - 1 to position.second)
                                0, 360 -> board[position.second - 1][position.first] to (position.first to position.second - 1)
                                else -> {
                                    println(direction)
                                    TODO()
                                }
                            }
                        } catch (e: ArrayIndexOutOfBoundsException) {
                            ' ' to position
                        }
                        when (nextMove) {
                            '.' -> position = newPosition
                            '#' -> return@forEach
                            ' ' -> {
                                position =
                                    when (direction) {
                                        90 -> {
                                            if (board[position.second].first { !it.isWhitespace() } != '#')
                                                board[position.second].indexOfFirst { !it.isWhitespace() } to position.second
                                            else return@forEach
                                        }

                                        180 -> {
                                            if (board.first { !it[position.first].isWhitespace() }[position.first] != '#')
                                                position.first to board.indexOfFirst { !it[position.first].isWhitespace() }
                                            else return@forEach
                                        }

                                        270 -> {
                                            if (board[position.second].last { !it.isWhitespace() } != '#')
                                                board[position.second].indexOfLast { !it.isWhitespace() } to position.second
                                            else return@forEach
                                        }

                                        0, 360 -> {
                                            if (board.last { it.size > position.first && !it[position.first].isWhitespace() }[position.first] != '#')
                                                position.first to board.indexOfLast { it.size > position.first && !it[position.first].isWhitespace() }
                                            else return@forEach
                                        }

                                        else -> {
                                            TODO()
                                        }
                                    }
                            }
                        }
                        number--
                    }
                }

            }

        }
        val facing = when (direction) {
            90 -> 0
            180 -> 1
            270 -> 2
            0, 360 -> 3
            else -> 0
        }
        val finalRow = position.second + 1
        val finalCol = position.first + 1
        val finalPassword = finalRow * 1000 + finalCol * 4 + facing
        return finalPassword
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day22_test")
    check(part1(testInput) == 6032)
    check(part2(testInput) == 0)

    val input = readInput("Day22")
    println(part1(input))
    println(part2(input))
}