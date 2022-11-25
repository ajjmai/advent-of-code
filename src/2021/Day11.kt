typealias Coordinate = Pair<Int, Int>
data class Octopus(val x: Int, val y: Int, var z: Int)

class Grid(input: List<String>) {
    private val grid: Map<Coordinate, Octopus> =
        input.flatMapIndexed{y, s ->
            s.mapIndexed { x, v ->
                Pair(x, y) to Octopus(x, y, v.toString().toInt())
            }
        }.toMap()

    private var steps = 0
    private var flashes = 0

    fun step() {
        steps++
        grid.values.forEach { it.z += 1 }
    }

    fun getSteps() = steps
    fun getFlashes() = flashes

    fun increaseAdjacent(adjacent: List<Coordinate>) =
        adjacent.forEach { grid[it]!!.z += 1}

    fun flash() {
        flashes += grid.values.filter { it.z > 9 }.size
        grid.values.forEach { if (it.z > 9) it.z = 0  }
    }

    fun getFlashing(alreadyFlashed: List<Coordinate>): List<Coordinate> =
        grid.values.filter { it.z > 9 }.map { Coordinate(it.x, it.y) }.minus(alreadyFlashed.toSet()).toList()

    fun isSynchronised() = grid.values.all { it.z == 0}

    fun getAdjacent(c: Coordinate): List<Coordinate> {
        return listOfNotNull(
            grid.getOrDefault(Coordinate(c.first - 1, c.second), null),
            grid.getOrDefault(Coordinate(c.first + 1, c.second), null),
            grid.getOrDefault(Coordinate(c.first, c.second - 1), null),
            grid.getOrDefault(Coordinate(c.first, c.second + 1), null),
            grid.getOrDefault(Coordinate(c.first - 1, c.second - 1), null),
            grid.getOrDefault(Coordinate(c.first - 1, c.second + 1), null),
            grid.getOrDefault(Coordinate(c.first + 1, c.second - 1), null),
            grid.getOrDefault(Coordinate(c.first + 1, c.second + 1), null),
        ).map { Coordinate(it.x, it.y) }
    }

    fun print() {
        val values = grid.values.map { it.z.toString() }.chunked(10)
        println("------------------")
        values.forEach { println(it)}
        println("------------------")
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val grid = Grid(input)

        for (i in 0 until 100) {
            grid.step()
            var alreadyFlashed = emptyList<Coordinate>()

            do {
                val flashing = grid.getFlashing(alreadyFlashed)
                alreadyFlashed += flashing
                val adjacent = flashing.flatMap { grid.getAdjacent(it) }.minus(alreadyFlashed.toSet()).toList()
                grid.increaseAdjacent(adjacent)
            } while (flashing.isNotEmpty())

            grid.flash()
        }

        return grid.getFlashes()
    }

    fun part2(input: List<String>): Int {
        val grid = Grid(input)

        do {
            grid.step()
            var alreadyFlashed = emptyList<Coordinate>()

            do {
                val flashing = grid.getFlashing(alreadyFlashed)
                alreadyFlashed += flashing
                val adjacent = flashing.flatMap { grid.getAdjacent(it) }.minus(alreadyFlashed.toSet()).toList()
                grid.increaseAdjacent(adjacent)
            } while (flashing.isNotEmpty())

            grid.flash()
        } while (!grid.isSynchronised())

        return grid.getSteps()
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))

}
