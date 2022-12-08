package days

import readInput

fun main() {
    tailrec fun sumFiles(
        filesystem: MutableList<Directory>,
        children: List<String>,
        sum: Long
    ): Long {
        var total = sum
        if (children.isEmpty()) return total
        val dirs = children.map { child ->
            filesystem.first { it.name == child }
        }
        dirs.forEach { dir ->
            total += dir.files.sumOf { it.size }
            sumFiles(filesystem, dir.childrenDirectories, total)
        }
        return total
    }


    fun part1(input: List<String>): Long {
        val filesystem: MutableList<Directory> = mutableListOf()
        var currentDirectory = ""
        input.forEach {
            when {
                it.startsWith("$ ls") -> return@forEach
                it.startsWith("$ cd") -> {
                    val dir = it.split(" ").last()
                    if (dir == "/" && filesystem.firstOrNull() == null) filesystem.add(
                        Directory(
                            "/",
                            parentDirectory = "/",
                            isHomeDirectory = true
                        )
                    )
                    val i = filesystem.indexOfFirst { it.name == currentDirectory }
                    currentDirectory = if (dir == "..") filesystem[i].parentDirectory else dir
                }

                it.startsWith("dir") -> {
                    val directoryName = it.split(" ").last()
                    val current = filesystem.first { it.name == currentDirectory }
                    current.childrenDirectories.add(directoryName)
                    val i = filesystem.indexOfFirst { it.name == directoryName }
                    if (i == -1) {
                        filesystem.add(Directory(directoryName, parentDirectory = currentDirectory))
                    } else {
                        filesystem[i]
                    }
                }

                else -> {
                    val i = filesystem.indexOfFirst { it.name == currentDirectory }
                    val dir = filesystem[i]
                    val (size, name) = it.split(" ")
                    dir.files.add(File(name, size.toLong()))
                }
            }
        }
        filesystem.forEach { d ->
            d.totalSize = sumFiles(
                filesystem,
                d.childrenDirectories,
                d.files.sumOf { it.size })
        }
        println(filesystem)
        return filesystem.filter { it.totalSize <= 100000 }.sumOf { it.totalSize }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 0)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

data class File(var name: String? = null, var size: Long = 0)
data class Directory(
    var name: String = "",
    var files: MutableList<File> = mutableListOf(),
    var parentDirectory: String = "",
    var childrenDirectories: MutableList<String> = mutableListOf(),
    var isHomeDirectory: Boolean = false,
    var totalSize: Long = 0L
)