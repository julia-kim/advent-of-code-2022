package days

import readInput


fun main() {

    fun part1(input: List<String>): Int {
        var array: Array<IntArray> = arrayOf()
        val rows = input[0].length
        val cols = input.size
        input.forEach {
            val intArray = it.map(Character::getNumericValue).toIntArray()
            array += intArray
        }
        val columns = List(cols) { col -> array.map { it[col] } }
        var visibleTrees = 0
        array.forEachIndexed { i, row ->
            row.forEachIndexed { j, height ->
                var visible = false
                val right = row.take(j)
                if (right.all { it < height }) visible = true
                val left = row.takeLast(rows - j - 1)
                if (left.all { it < height }) visible = true
                val top = columns[j].take(i)
                if (top.all { it < height }) visible = true
                val bottom = columns[j].takeLast(cols - i - 1)
                if (bottom.all { it < height }) visible = true
                if (visible) visibleTrees++
            }
        }
        return visibleTrees
    }

    fun part2(input: List<String>): Int {
        var array: Array<IntArray> = arrayOf()
        val rows = input[0].length
        val cols = input.size
        input.forEach {
            val intArray = it.map(Character::getNumericValue).toIntArray()
            array += intArray
        }
        val columns = List(cols) { col -> array.map { it[col] } }
        var scenicScore: MutableList<Int> = mutableListOf()
        array.forEachIndexed { i, row ->
            row.forEachIndexed { j, height ->

                val right = row.take(j).reversed().takeIf { it.isNotEmpty() }
                val r = right?.indexOfFirst { it >= height }?.let { right.take(it)?.size ?: 0 }
                val left = row.takeLast(rows - j - 1).takeIf { it.isNotEmpty() }
                val l = left?.takeWhile { it < height }?.size ?: 0
                val top = columns[j].take(i).reversed().takeIf { it.isNotEmpty() }
                val t = top?.takeWhile { it < height }?.size ?: 0
                val bottom = columns[j].takeLast(cols - i - 1).takeIf { it.isNotEmpty() }
                val b = bottom?.takeWhile { it < height }?.size ?: 0
                val score = r * l * t * b
                scenicScore.add(score)
            }
        }
        println(scenicScore)
        return scenicScore.max()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}