package y2023

import readInput

fun main() {
    fun stringListToIntList(input: List<String>) = input.map { it.toInt() }

    fun calculateCaloriesPerElf(input: List<String>): List<Int> {
        val splits = input.mapIndexedNotNull{ index, elem -> index.takeIf{ elem == "" } }
        val sums: MutableList<Int> = mutableListOf()

        for ((index, value) in splits.withIndex()) {
            if (index == 0) {
                val sum = stringListToIntList(input.slice(0 until value)).sum()
                sums.add(sum)
            } else {
                val sum = stringListToIntList(input.slice(splits[index - 1] + 1 until value)).sum()
                sums.add(sum)
            }
        }

        val sum = stringListToIntList(input.slice(splits.last() + 1 until input.size)).sum()
        sums.add(sum)
        return sums
    }

    fun part1(input: List<String>): Int? {
        val calories = calculateCaloriesPerElf(input)
        return calories.maxOrNull()
    }

    fun part2(input: List<String>): Int {
        val calories = calculateCaloriesPerElf(input)
        return calories.sortedDescending().take(3).sum()
    }

    val testInput = readInput("Day01_test", "y2024")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01", "y2024")
    println(part1(input))
    println(part2(input))
}
