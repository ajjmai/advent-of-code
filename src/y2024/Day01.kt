package y2024

import readInput
import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        val parsedInput = input.map { it.split("   ") }.map { it.map(String::toInt) }
        return Pair(parsedInput.map { it[0] }, parsedInput.map { it[1] })
    }

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)
        return firstList.sorted().zip(secondList.sorted()) { a, b -> abs(a - b) }.sum()
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)
        val secondListCounts = secondList.groupingBy { it }.eachCount()
        val occurrences = firstList.map { secondListCounts[it] ?: 0 }
        return firstList.zip(occurrences) { a, b -> a * b }.sum()
    }

    val testInput = readInput("Day01_test", "y2024")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01", "y2024")
    check(part1(input) == 2192892)
    check(part2(input) == 22962826)
}
