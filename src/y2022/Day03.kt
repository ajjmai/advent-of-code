package y2022

import readInput

fun main() {

    fun findDuplicateChars(string1: String, string2:String): Set<Char> {
        return string1.toList().intersect(string2.toList().toSet())
    }

    fun getSumOfPriorities(items: List<Char>): Int {
        return items.sumOf {
            if (it.isLowerCase()) it.code - 96
            else it.code - 38
        }
    }

    fun part1(input: List<String>): Int {
        val duplicateItems: MutableList<Char> = mutableListOf()
        input.forEach {
            val parts = it.chunked(it.length / 2)
            duplicateItems += findDuplicateChars(parts[0], parts[1])
        }
        return getSumOfPriorities(duplicateItems)
    }

    fun part2(input: List<String>): Int {
        val triplecateItems: MutableList<Char> = mutableListOf()
        input.chunked(3).forEach {
            triplecateItems += findDuplicateChars(it[0], findDuplicateChars(it[1], it[2]).toString())
        }
        return getSumOfPriorities(triplecateItems)
    }

    val testInput = readInput("Day03_test", "2022")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03", "2022")
    println(part1(input))
    println(part2(input))
}
