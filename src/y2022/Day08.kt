package y2022

import readInput
import stringsToInt

inline fun List<Int>.takeWhileInclusive(
    predicate: (Int) -> Boolean
): List<Int> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

fun main() {
    fun getTrees(input: List<String>) = input.map { it.chunked(1) }.map { stringsToInt(it) }

    fun part1(input: List<String>): Int {
        val trees = getTrees(input)

        val width = trees.first().size
        val length = trees.size
        val edgeTrees = width * 2 + (length - 2) * 2

        var insideTrees = 0

        for (row in 1 until trees.size - 1) {
            for (col in 1 until trees[row].size - 1) {
                val tree = trees[row][col]
                val leftMax = trees[row].slice(0 until col).maxOrNull() ?: 0
                val rightMax = trees[row].slice(col + 1 until trees.size).maxOrNull() ?: 0
                val upMax = trees.slice(0 until row).map { it[col] }.maxOrNull() ?: 0
                val downMax = trees.slice(row + 1 until trees.size).map { it[col] }.maxOrNull() ?: 0

                if (tree > leftMax || tree > rightMax || tree > upMax || tree > downMax)  {
                    insideTrees += 1
                }
            }
        }
        return insideTrees + edgeTrees
    }

    fun part2(input: List<String>): Int {
        val trees = getTrees(input)

        var maxScenicScore = 0
        for (row in 1 until trees.size - 1) {
            for (col in 1 until trees[row].size - 1) {
                val tree = trees[row][col]
                val left = trees[row].slice(0 until col).reversed().takeWhileInclusive { it < tree }.size
                val right = trees[row].slice(col + 1 until trees.size).takeWhileInclusive { it < tree }.size
                val up = trees.slice(0 until row).map { it[col] }.reversed().takeWhileInclusive { it < tree }.size
                val down = trees.slice(row + 1 until trees.size).map { it[col] }.takeWhileInclusive { it < tree }.size

                maxScenicScore = maxOf(maxScenicScore, left * right * up * down)
            }
        }
        return maxScenicScore
    }

    val testInput = readInput("Day08_test", "y2022")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08", "y2022")
    println(part1(input))
    println(part2(input))
}
