package days

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val filesystem: MutableMap<String, MutableSet<String>> = mutableMapOf()
        val allDirectories: MutableMap<String, Directory> = mutableMapOf()
        var currentDirectory: String = ""
        var totalSize = 0L
        input.forEach {
            when {
                it.startsWith("$ ls") -> return@forEach

                it.startsWith("$ cd") -> {
                    val dir = it.split(" ").last()
                    if (dir == "/" && allDirectories["/"] == null) allDirectories["/"] =
                        Directory("/", parentDirectory = "/", isHomeDirectory = true)
                    currentDirectory = if (dir == "..") allDirectories[currentDirectory]!!.parentDirectory else dir
                }

                it.startsWith("dir") -> {
                    val directoryName = it.split(" ").last()
                    if (filesystem[currentDirectory] == null) {
                        filesystem[currentDirectory] = mutableSetOf(directoryName)
                    } else {
                        filesystem[currentDirectory]!!.add(directoryName)
                    }

                    if (allDirectories[directoryName] == null) {
                        allDirectories[directoryName] = Directory(directoryName, parentDirectory = currentDirectory)
                    }
                }

                else -> {
                    val dir = allDirectories[currentDirectory]!!
                    val (size, name) = it.split(" ")
                    dir.files.add(File(name, size.toLong()))
                }
            }
        }
        allDirectories.forEach { it.value.folderSize = it.value.files.sumOf { it.size } }
        allDirectories.filter { it.value.folderSize <= 100000 }
        return totalSize
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
    var name: String? = null,
    var files: MutableList<File> = mutableListOf(),
    var parentDirectory: String = "",
    var isHomeDirectory: Boolean = false,
    var folderSize: Long = 0
)