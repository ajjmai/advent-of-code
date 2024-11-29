package y2023

import readInput

fun main() {

    val red = 12
    val green = 13
    val blue = 14

    val colors = mapOf("red" to red, "green" to green, "blue" to blue)

    fun parseInput(input: List<String>): List<List<Pair<String, Int>>> {
        return input.map { line ->
            line.substringAfter(":").split(";").flatMap {
                it.split(",").map { cubes ->
                    val (value, color) = cubes.trim().split(" ")
                    color to value.toInt()
                }
            }
        }
    }

    fun getPossibleGames(input: List<List<Pair<String, Int>>>): List<Int> {
        return input.mapIndexedNotNull { index, line ->
            if (line.all { (color, value) -> value <= colors[color]!! }) index + 1 else null
        }
    }

    fun getMinimunCubes(input: List<List<Pair<String, Int>>>): Int {
        return input.sumOf { line ->
            line.groupBy { it.first }
                .mapValues { it.value.maxOfOrNull { pair -> pair.second } ?: 0 }
                .values.fold(1) { acc, value -> acc * value }.toInt() }
    }

    fun part1(input: List<String>): Int {
        return getPossibleGames(parseInput(input)).sum()
    }

    fun part2(input: List<String>): Int {
        return getMinimunCubes(parseInput(input))
    }

    val testInput = readInput("Day02_test", "y2023")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02", "y2023")
    check(part1(input) == 2563)  // 2563
    check(part2(input) == 70768)  // 70768
}
