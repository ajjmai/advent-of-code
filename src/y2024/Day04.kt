package y2024

import readInput

fun main() {
    fun countXMAS(input: String) = input.windowed(4).count { it == "XMAS" }
    fun countXMASinRotated(input: String) = input.windowed(7).count { it == "X.M.A.S" }

    fun transpose(input: List<String>): List<String> {
        val maxLength = input.maxOf { it.length }
        return List(maxLength) { i ->
            input.joinToString("") { it.getOrNull(i)?.toString() ?: " " }
        }
    }

    fun rotateClockwise(input: List<String>): List<String> {
        val maxLength = input.size + input[0].length - 1
        val tilted = MutableList(maxLength) { MutableList(maxLength) { '.' } }
        for (j in input.indices) {
            for (i in input[j].indices) {
                tilted[input.size - 1 + i - j][i + j] = input[j][i]
            }
        }
        return tilted.map { it.joinToString("") }

    }

    fun rotateBackFromClockwise(masLocation: Pair<Int, Int>, n: Int): Pair<Int, Int> {
        val (x, y) = masLocation
        return Pair((x + y - (n - 1)) / 2, (x - y + (n - 1)) / 2)
    }

    fun rotateCounterClockwise(input: List<String>): List<String> {
        val maxLength = input.size + input[0].length - 1
        val tilted = MutableList(maxLength) { MutableList(maxLength) { '.' } }
        for (j in input.indices) {
            for (i in input[j].indices) {
                tilted[i + j][input[j].length - 1 + i - j] = input[j][i]
            }
        }
        return tilted.map { it.joinToString("") }

    }

    fun rotateBackFromCounterClockwise(masLocation: Pair<Int, Int>, m: Int): Pair<Int, Int> {
        val (x, y) = masLocation
        return Pair((x + y - (m - 1)) / 2, (y - x + (m - 1)) / 2)
    }

    fun findMASIndexes(input: List<String>): List<Pair<Int, Int>> {
        return input.flatMapIndexed { index, line ->
            line.windowed(5).withIndex().filter { it.value == "M.A.S" || it.value == "S.A.M" }
                .map { Pair(it.index + 2, index) }
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { countXMAS(it) + countXMAS(it.reversed()) } +
                transpose(input).sumOf { countXMAS(it) + countXMAS(it.reversed()) } +
                rotateCounterClockwise(input).sumOf { countXMASinRotated(it) + countXMASinRotated(it.reversed()) } +
                rotateClockwise(input).sumOf { countXMASinRotated(it) + countXMASinRotated(it.reversed()) }
    }

    fun part2(input: List<String>): Int {
        val leftTilted = findMASIndexes(rotateCounterClockwise(input)).map {
            rotateBackFromCounterClockwise(
                it,
                input.first().length
            )
        }.toSet()
        val rightTilted = findMASIndexes(rotateClockwise(input)).map { rotateBackFromClockwise(it, input.size) }.toSet()

        return leftTilted.intersect(rightTilted).size
    }

    val testInput = readInput("Day04_test", "y2024")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04", "y2024")
    check(part1(input) == 2532)
    check(part2(input) == 1941)
}
