package y2024

import readInput

fun main() {
    val robotRegex = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()

    val gridWidth = 101
    val gridHeight = 103

    data class Quadrant(val x1: Int, val x2: Int, val y1: Int, val y2: Int)

    class Robot(px: Int, py: Int, vx: Int, vy: Int) {
        private var x = px
        private var y = py
        private val velocity = Pair(vx, vy)

        fun move() {
            x = (x + velocity.first + gridWidth) % gridWidth
            y = (y + velocity.second + gridHeight) % gridHeight

        }

        fun isInsideQuadrant(q: Quadrant) = x in q.x1..q.x2 && y in q.y1..q.y2

        fun isInTile(x: Int, y: Int) = this.x == x && this.y == y
    }

    val fullMap = Quadrant(0, gridWidth - 1, 0, gridHeight - 1)

    val quadrants = listOf(
        Quadrant(0, gridWidth / 2 - 1, 0, gridHeight / 2 - 1),
        Quadrant(gridWidth / 2 + 1, gridWidth - 1, 0, gridHeight / 2 - 1),
        Quadrant(0, gridWidth / 2 - 1, gridHeight / 2 + 1, gridHeight - 1),
        Quadrant(gridWidth / 2 + 1, gridWidth - 1, gridHeight / 2 + 1, gridHeight - 1),
    )

    fun printMap(robots: List<Robot>, q: Quadrant) {
        for (y in q.y1..q.y2) {
            for (x in q.x1..q.x2) {
                print(if (robots.any { it.isInTile(x, y) }) "#" else ".")
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val robots = input.map { line ->
            val (px, py, vx, vy) = robotRegex.find(line)!!.destructured
            Robot(px.toInt(), py.toInt(), vx.toInt(), vy.toInt())
        }

        repeat(100) {
            robots.forEach { it.move() }
        }

        return quadrants.map { q -> robots.filter { it.isInsideQuadrant(q) }.size }.reduce { a, b -> a * b }
    }

    fun part2(input: List<String>): Int {
        val robots = input.map { line ->
            val (px, py, vx, vy) = robotRegex.find(line)!!.destructured
            Robot(px.toInt(), py.toInt(), vx.toInt(), vy.toInt())
        }
        var s = 0
        repeat(8168) {
            robots.forEach { it.move() }
            s++
        }
        printMap(robots, fullMap)
        return s
    }

    val testInput = readInput("Day14_test", "y2024")
    check(part1(testInput) == 21)

    val input = readInput("Day14", "y2024")
    check(part1(input) == 226236192)
    check(part2(input) == 8168)
}
