package days

import readInput


fun main() {
    fun createTreeGrid(input: List<String>) = input.map {
        it.map(Character::getNumericValue).toIntArray()
    }.toTypedArray()

    fun part1(input: List<String>): Int {
        val array = createTreeGrid(input)
        val cols = input[0].length
        val rows = input.size
        val columns = List(cols) { col -> array.map { it[col] } }
        var visibleTrees = 0
        array.forEachIndexed { i, row ->
            row.forEachIndexed { j, height ->
                var visible = false
                val left = row.take(j)
                if (left.all { it < height }) visible = true
                val right = row.takeLast(cols - j - 1)
                if (right.all { it < height }) visible = true
                val top = columns[j].take(i)
                if (top.all { it < height }) visible = true
                val bottom = columns[j].takeLast(rows - i - 1)
                if (bottom.all { it < height }) visible = true
                if (visible) visibleTrees++
            }
        }
        return visibleTrees
    }

    fun part2(input: List<String>): Int {
        val array = createTreeGrid(input)
        val cols = input[0].length
        val rows = input.size
        val columns = List(cols) { col -> array.map { it[col] } }
        val scenicScore: MutableList<Int> = mutableListOf()
        array.forEachIndexed { i, row ->
            row.forEachIndexed { j, height ->
                val left = row.take(j).reversed().takeIf { it.isNotEmpty() }
                val l = left?.indexOfFirst { it >= height }?.let { if (it == -1) left.size else it + 1 } ?: 0
                val right = row.takeLast(cols - j - 1).takeIf { it.isNotEmpty() }
                val r = right?.indexOfFirst { it >= height }?.let { if (it == -1) right.size else it + 1 } ?: 0
                val top = columns[j].take(i).reversed().takeIf { it.isNotEmpty() }
                val t = top?.indexOfFirst { it >= height }?.let { if (it == -1) top.size else it + 1 } ?: 0
                val bottom = columns[j].takeLast(rows - i - 1).takeIf { it.isNotEmpty() }
                val b = bottom?.indexOfFirst { it >= height }?.let { if (it == -1) bottom.size else it + 1 } ?: 0
                val score = r * l * t * b
                scenicScore.add(score)
            }
        }
        return scenicScore.max()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}