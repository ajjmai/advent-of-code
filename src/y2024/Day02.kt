package y2024

import readInput
import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>) = input.map { it.split(" ").map(String::toInt) }

    fun calculateDifferences(report: List<Int>) = report.zipWithNext { a, b -> a - b }

    fun getNumberOfSafeReports(differences: List<List<Int>>) =
        differences.filter { report -> report.all { abs(it) in 1..3 } && (report.all { it < 0 } || report.all { it > 0 }) }.size

    fun part1(input: List<String>): Int {
        return getNumberOfSafeReports(parseInput(input).map { calculateDifferences(it) })
    }

    fun part2(input: List<String>): Int {
        return parseInput(input).count { report ->
            val newReports = mutableListOf<List<Int>>()
            report.forEach { newReports.add(report - it) }
            getNumberOfSafeReports(newReports.map { calculateDifferences(it) }) > 0
        }
    }

    val testInput = readInput("Day02_test", "y2024")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02", "y2024")
    check(part1(input) == 686)
    check(part2(input) == 717)
}
