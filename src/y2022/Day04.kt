package y2022

import readInput

fun main() {
    fun parseAreas(input: List<String>) = input.chunked(1).map {
        it.flatMap { it2 -> it2.split(',', '-').map { it3 -> it3.toInt() } }
    }

    fun rangeIsInsideOtherRange(range1: IntRange, range2: IntRange) =
        range1.first in range2 && range1.last in range2 || range2.first in range1 && range2.last in range1

    fun rangesOverlap(range1: IntRange, range2: IntRange) =
        range1.first <= range2.last && range2.first <= range1.last

    fun part1(input: List<String>): Int {
        val areas = parseAreas(input)
        var overlaps = 0
        areas.forEach {
            if (rangeIsInsideOtherRange(it[0]..it[1], it[2]..it[3])) overlaps += 1
        }
        return overlaps
    }

    fun part2(input: List<String>): Int {
        val areas = parseAreas(input)
        var overlaps = 0
        areas.forEach {
            if (rangesOverlap(it[0]..it[1], it[2]..it[3])) overlaps += 1
        }
        return overlaps
    }

    val testInput = readInput("Day04_test", "y2022")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04", "y2022")
    println(part1(input))
    println(part2(input))
}
