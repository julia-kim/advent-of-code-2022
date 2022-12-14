package days

import readInput

fun main() {
    fun Int.isOutOfBounds(upperLimit: Int): Boolean {
        return (this < 0 || this > upperLimit)
    }

    fun Long.isOutOfBounds(upperLimit: Int): Boolean {
        return (this < 0 || this > upperLimit)
    }

    fun part1(input: List<String>): Int {
        val encryptedFile = input.mapIndexed { i, num -> i to num.toInt() }
        val mixedFile = encryptedFile.toMutableList()
        val size = encryptedFile.size - 1
        encryptedFile.forEach { pair ->
            val originalIndex = mixedFile.indexOfFirst { it == pair }
            mixedFile.remove(pair)
            var newIndex = originalIndex + pair.second
            while (newIndex.isOutOfBounds(size)) {
                if (newIndex > 0) newIndex -= size
                else newIndex += size
            }

            mixedFile.add(newIndex, pair)
        }

        val zeroIndex = mixedFile.indexOf(encryptedFile.first { it.second == 0 })
        val groveCoordinates = listOf(1000, 2000, 3000).map {
            var i = it + zeroIndex
            while (i.isOutOfBounds(size)) {
                i -= size + 1
            }
            mixedFile[i].second
        }
        println(groveCoordinates)
        return groveCoordinates.sum()
    }

    fun part2(input: List<String>): Long {
        val decryptionKey = 811589153L
        val encryptedFile = input.mapIndexed { i, num -> i to num.toLong() * decryptionKey }
        val mixedFile = encryptedFile.toMutableList()
        val size = encryptedFile.size - 1
        repeat(10) {
            // % remainder??
            encryptedFile.forEach { pair ->
                val originalIndex = mixedFile.indexOfFirst { it == pair }
                mixedFile.remove(pair)
                var newIndex = originalIndex + pair.second
                while (newIndex.isOutOfBounds(size)) {
                    if (newIndex > 0) newIndex -= size
                    else newIndex += size
                }

                mixedFile.add(newIndex.toInt(), pair)
            }
        }

        val zeroIndex = mixedFile.indexOf(encryptedFile.first { it.second == 0L })
        val groveCoordinates = listOf(1000, 2000, 3000).map {
            var i = it + zeroIndex
            while (i.isOutOfBounds(size)) {
                i -= size + 1
            }
            mixedFile[i].second
        }
        println(groveCoordinates)
        return groveCoordinates.sum()
    }

    val testInput = readInput("Day20_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 1623178306L)

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}