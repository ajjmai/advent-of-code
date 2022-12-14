package y2022

import readInput
import kotlin.math.max
import kotlin.math.min

class Cave(private val x: Int, y: Int, private val xScaler: Int, private val floorChar: String = ".") {
    private val map = List(y) { MutableList(x) { "." } } + List(1) { MutableList(x) { floorChar } }
    private val sandStartPoint = Pair(500 - xScaler, 0)

    fun markRocks(start: Pair<Int, Int>, end: Pair<Int, Int>) {
        val xRange = if (start.first <= end.first) start.first..end.first else end.first..start.first
        val yRange = if (start.second <= end.second) start.second..end.second else end.second..start.second

        for (x in xRange) {
            for (y in yRange) {
                map[y][x - xScaler] = "#"
            }
        }
    }

    fun sandAmount() = map.flatten().count { it == "o" }

    fun sandFall(): Boolean {
        var (sandX, sandY) = sandStartPoint
        while (true) {
            if (sandY + 1 >= map.size || sandX - 1 < 0 || sandX + 1 >= map.first().size) {
                return false
            }
            if (map[sandY + 1][sandX] == ".") {
                sandY += 1
            } else if (map[sandY + 1][sandX - 1] == ".") {
                sandX -= 1
                sandY += 1
            } else if (map[sandY + 1][sandX + 1] == ".") {
                sandX += 1
                sandY += 1
            } else {
                map[sandY][sandX] = "o"
                return true
            }
        }
    }

    fun sandFallWithFloor(): Boolean {
        var (sandX, sandY) = sandStartPoint
        while (true) {
            if (map[0][500 - xScaler] == "o") {
                return false
            }
            if (map[sandY + 1][sandX] == ".") {
                sandY += 1
            } else if (map[sandY + 1][sandX - 1] == ".") {
                sandX -= 1
                sandY += 1
            } else if (map[sandY + 1][sandX + 1] == ".") {
                sandX += 1
                sandY += 1
            } else {
                map[sandY][sandX] = "o"
                return true
            }
        }
    }

    fun print() {
        map.forEach { println(it.joinToString("")) }
    }
}

fun main() {

    fun getRanges(input: List<String>): Triple<Int, Int, Int> {
        val x = input.map { it.substringBefore(',').toInt() }
        val xMax = x.maxOrNull()!!
        val xMin = x.minOrNull()!!
        val yMax = input.maxOf { it.substringAfter(',').toInt() }
        return Triple(max(xMax, 1000 - xMax), min(xMin, 1000 - xMax), yMax)
    }

    fun getLineEndPoint(string: String ) = string.split(',').map { l -> l.toInt() }.zipWithNext().first()

    fun part1(input: List<String>): Int {
        val (maxX, minX, maxY) = getRanges(input.flatMap { it.split(" -> ") })
        val cave = Cave(maxX - minX + 6, maxY + 1, minX - 3)

        input.map { it.split(" -> ").zipWithNext() }.forEach {
            it.forEach { line ->
                cave.markRocks(getLineEndPoint(line.first), getLineEndPoint(line.second))
            }
        }

        var isRoomForMoreSand = true
        while (isRoomForMoreSand) {
            isRoomForMoreSand = cave.sandFall()
        }

        cave.print()
        return cave.sandAmount()
    }

    fun part2(input: List<String>): Int {
        val (maxX, minX, maxY) = getRanges(input.flatMap { it.split(" -> ") })
        val cave = Cave(maxX - minX + 280, maxY + 2, minX - 140, "#")

        input.map { it.split(" -> ").zipWithNext() }.forEach {
            it.forEach { line ->
                cave.markRocks(getLineEndPoint(line.first), getLineEndPoint(line.second))
            }
        }

        var isRoomForMoreSand = true
        while (isRoomForMoreSand) {
            isRoomForMoreSand = cave.sandFallWithFloor()
        }
        cave.print()

        return cave.sandAmount()
    }

    val testInput = readInput("Day14_test", "y2022")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14", "y2022")
    println(part1(input))
    println(part2(input))
}
