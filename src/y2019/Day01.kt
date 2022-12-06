package y2019

import readInput
import stringsToInt
import kotlin.math.floor

fun main() {

    fun part1(input: List<String>): Int {
        val masses = stringsToInt(input)
        return masses.sumOf {
            floor(it / 3.0) - 2
        }.toInt()
    }

    fun part2(input: List<String>): Int {
        val masses = stringsToInt(input)
        var totalFuel = 0
        masses.forEach {
            var sum = 0
            var x = it
            while (x >= 0) {
                x = floor(x / 3.0).toInt() - 2
                if (x >= 0) sum += x
            }
            totalFuel += sum
        }
        return totalFuel
    }

    val testInput = listOf("100756")
    check(part1(testInput) == 33583)
    check(part2(testInput) == 50346)

    val input = readInput("Day01", "y2019")
    println(part1(input))
    println(part2(input))
}
