package y2022

import readInput

fun main() {

    fun isAllCharsUnique(string: String): Boolean = string.all(hashSetOf<Char>()::add)

    fun part1(input: List<String>): Int {
        val string = input.first()
        for (i in 4 until string.length) {
            if (isAllCharsUnique(string.substring(i - 4, i))) return i
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val string = input.first()
        for (i in 14 until string.length) {
            if (isAllCharsUnique(string.substring(i - 14, i))) return i
        }
        return -1
    }

    val testInput = readInput("Day06_test", "y2022")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06", "y2022")
    println(part1(input))
    println(part2(input))
}
