package y2024

import readInput

fun main() {
    fun calculateAllEquations(numbers: List<Long>, partTwo: Boolean): List<Long> {
        fun helper(index: Int, current: Long, results: MutableList<Long>) {
            if (index == numbers.size) {
                results.add(current)
                return
            }
            helper(index + 1, current + numbers[index], results)
            helper(index + 1, current * numbers[index], results)
            if (partTwo) helper(index + 1, (current.toString() + numbers[index].toString()).toLong(), results)
        }

        val results = mutableListOf<Long>()
        if (numbers.isNotEmpty()) {
            helper(1, numbers[0], results)
        }
        return results
    }

    fun findTrueEquationsSum(equations: List<Pair<Long, List<Long>>>, partTwo: Boolean = false): Long {
        return equations.filter { eq ->
            calculateAllEquations(eq.second, partTwo).contains(eq.first)
        }.sumOf { it.first }
    }

    fun parseEquations(input: List<String>) =
        input.map { it.split(": ") }.map { it.first().toLong() to it.last().split(" ").map(String::toLong) }

    fun part1(input: List<String>) = findTrueEquationsSum(parseEquations(input))

    fun part2(input: List<String>) = findTrueEquationsSum(parseEquations(input), true)

    val testInput = readInput("Day07_test", "y2024")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07", "y2024")
    check(part1(input) == 3351424677624)
    check(part2(input) == 204976636995111)
}
