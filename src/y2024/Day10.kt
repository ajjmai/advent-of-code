package y2024

import readInput

class Point(val x: Int, val y: Int, val value: Int) {
    fun isTrailHead() = value == 0

    fun isAdjacent(other: Point) =
        Pair(this.x - other.x, this.y - other.y) in listOf(1 to 0, -1 to  0, 0 to 1, 0 to -1)
}

class TrailMap(private val points: List<Point>) {
    fun getTrailHeads() = points.filter { it.isTrailHead() }!!
    fun getNextSteps(point: Point) = points.filter { it.isAdjacent(point) && it.value - point.value == 1 }
}


fun main() {
fun parsePoints(input: List<String>) = input.flatMapIndexed { y, line -> line.mapIndexed { x, char -> Point(x, y, char.digitToInt()) } }

    fun part1(input: List<String>): Int {
        val trailMap = TrailMap(parsePoints(input))
        val possibleTrailHeads = trailMap.getTrailHeads()

        return possibleTrailHeads.sumOf { th ->
            var nextSteps = trailMap.getNextSteps(th).toSet()
            repeat(8) { nextSteps = nextSteps.flatMap { trailMap.getNextSteps(it) }.toSet() }
            nextSteps.size
        }
    }

    fun part2(input: List<String>): Int {
        val trailMap = TrailMap(parsePoints(input))
        val possibleTrailHeads = trailMap.getTrailHeads()

        return possibleTrailHeads.sumOf { th ->
            var paths = trailMap.getNextSteps(th).map { listOf(th, it) }.toMutableList()
            repeat(8) {
                paths = paths.flatMap { path ->
                    trailMap.getNextSteps(path.last()).map { path + it }
                }.toMutableList()
            }
            paths.size
        }
    }

    val testInput = readInput("Day10_test", "y2024")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput("Day10", "y2024")
    check(part1(input) == 468)
    check(part2(input) == 966)
}
