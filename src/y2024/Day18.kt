package y2024

import readInput
import java.util.*

fun main() {
    fun isValidMove(grid: List<List<String>>, visited: Array<Array<Boolean>>, x: Int, y: Int): Boolean {
        return x in grid.indices && y in grid[0].indices && grid[x][y] == "." && !visited[x][y]
    }

    //    BFS
    fun shortestPath(grid: List<List<String>>): Int {
        val n = grid.size
        val directions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))
        val visited = Array(n) { Array(n) { false } }
        val queue: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()

        queue.add(Pair(0, 0) to 0) // start (0,0)
        visited[0][0] = true

        while (queue.isNotEmpty()) {
            val (current, distance) = queue.poll()
            val (x, y) = current

            // goal (n-1, n-1)
            if (x == n - 1 && y == n - 1) {
                return distance
            }

            for ((dx, dy) in directions) {
                val newX = x + dx
                val newY = y + dy

                if (isValidMove(grid, visited, newX, newY)) {
                    queue.add(Pair(newX, newY) to distance + 1)
                    visited[newX][newY] = true
                }
            }
        }

        return -1 // Return -1 if there is no path
    }

    fun parseBytes(input: List<String>) = input.map {byte -> byte.split(",").let { it.first().toInt() to it.last().toInt() } }
    fun initGrid (n: Int) = List(n) { MutableList(n) { "." } }

    fun part1(input: List<String>, n: Int, fallBytes: Int): Int {
        val bytes = parseBytes(input)
        val grid = initGrid(n)
        bytes.take(fallBytes).forEach { (x, y) -> grid[y][x] = "#" }
        return shortestPath(grid)
    }

    fun part2(input: List<String>, n: Int, fallBytes: Int): Pair<Int, Int> {
        val bytes = parseBytes(input)
        val grid = initGrid(n)
        var byte = 0
        bytes.take(fallBytes).forEach { (x, y) -> grid[y][x] = "#" }
        bytes.drop(fallBytes).forEach { (x, y) ->
            grid[y][x] = "#"
            if (shortestPath(grid) == -1) return Pair(x, y)
        }
        return Pair(-1, -1)
    }

    val nTest = 7
    val n = 71

    val fallTest = 12
    val fall = 1024

    val testInput = readInput("Day18_test", "y2024")
    check(part1(testInput, nTest, fallTest) == 22)
    check(part2(testInput, nTest, fallTest) == Pair(6, 1))

    val input = readInput("Day18", "y2024")
    check(part1(input, n, fall) == 288)
    check(part2(input, n, fall) == Pair(52, 5))
}
