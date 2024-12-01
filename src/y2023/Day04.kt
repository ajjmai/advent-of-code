package y2023

import readInput
import kotlin.math.pow

fun main() {
    fun parseCards(input: List<String>): List<List<List<String>>> {
        return input.map { card -> card.split(": ", " | ").drop(1).map { it.split("  ", " ") } }
    }

    fun getMatches(card: List<List<String>>): Int {
        return card[0].intersect(card[1].toSet()).size
    }

    fun getPoints(size: Int): Int {
        return if (size == 0) 0 else 2.0.pow(size - 1).toInt()
    }

    fun part1(input: List<String>): Int {
        return parseCards(input).sumOf { getPoints(getMatches(it)) }
    }

    fun part2(input: List<String>): Int {
        val numberOfCards = MutableList(input.size) { 1 }
        val pointsInCards = parseCards(input).map { getMatches(it) }
        pointsInCards.forEachIndexed { index, points ->
            repeat(numberOfCards[index]) {
                for (point in 1..points) {
                    numberOfCards[index + point] += 1
                }
            }
        }
        return numberOfCards.sum()
    }

    val testInput = readInput("Day04_test", "y2023")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04", "y2023")
    check(part1(input) == 18653)
    check(part2(input) == 5921508)
}
