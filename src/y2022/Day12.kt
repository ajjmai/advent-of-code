package y2022

import readInput
import kotlin.math.abs

class Point(val x: Int, val y: Int, private val elevation: Char) {
    fun isBestSignal() = this.elevation == 'E'
    fun isStart() = this.elevation == 'S'

    fun getElevation() = this.elevation

    fun canStep(other: Point): Boolean {
        return if (this.elevation == 'S' && other.elevation == 'a') true
        else if (other.elevation == 'E') this.elevation.code - 'z'.code in -1..0
        else this.elevation.code - other.elevation.code >= -1
    }

    private val validMoves = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))

    fun isAdjacent(other: Point) = Pair(this.x - other.x, this.y - other.y) in validMoves

    override fun toString() = this.elevation.toString()
}

class HeightMap(private val width: Int) {
    private val points: MutableList<Point> = mutableListOf()

    fun addPoint(point: Point) = this.points.add(point)

    fun getStartingPoint() = points.find { it.isStart() }!!
    fun getBestSignalPoint() = points.find { it.isBestSignal() }!!
    fun getAllPointsOfElevation(elevation: Char) = points.filter { it.getElevation() == elevation }

    fun getNeighbours(point: Point): List<Point> = points.filter { it.isAdjacent(point) && point.canStep(it) }

    fun print() = points.chunked(width).forEach { println(it.toString()) }
}

fun getEstimatedDistance(start: Point, end: Point): Int {
    val dx = abs(start.x - end.x)
    val dy = abs(start.y - end.y)
    return (dx + dy) + (-2) * minOf(dx, dy)
}

fun generatePath(currentPoint: Point, cameFrom: Map<Point, Point>): List<Point> {
    val path = mutableListOf(currentPoint)
    var current = currentPoint
    while (cameFrom.containsKey(current)) {
        current = cameFrom.getValue(current)
        path.add(0, current)
    }
    return path.toList()
}

fun aStar(startingPoint: Point, bestSignal: Point, map: HeightMap): Int {

    val nextSteps: MutableList<Point> = mutableListOf(startingPoint)
    val visited: MutableList<Point> = mutableListOf()

    val costFromStart = mutableMapOf(startingPoint to 0)
    val estimatedTotalCost = mutableMapOf(startingPoint to getEstimatedDistance(startingPoint, bestSignal))

    val cameFrom = mutableMapOf<Point, Point>()

    while (nextSteps.isNotEmpty()) {
        val currentPoint = nextSteps.minByOrNull { estimatedTotalCost.getValue(it) }!!

        if (currentPoint.isBestSignal()) {
            val path = generatePath(currentPoint, cameFrom)
            return path.size - 1
        }

        nextSteps.remove(currentPoint)
        visited.add(currentPoint)

        map.getNeighbours(currentPoint).filterNot {
            visited.contains(it)
        }.forEach { neighbour ->
            val score = costFromStart.getValue(currentPoint) + 1
            if (!nextSteps.contains(neighbour)) {
                nextSteps.add(neighbour)
            }
            cameFrom[neighbour] = currentPoint
            costFromStart[neighbour] = score
            estimatedTotalCost[neighbour] = score + getEstimatedDistance(neighbour, bestSignal)
        }
    }
    return 0
}

fun main() {

    fun initHeightMap(input: List<String>): HeightMap {
        val heightMap = input.map { it.chunked(1) }
        val map = HeightMap(heightMap.first().size)

        heightMap.forEachIndexed { y, line ->
            line.forEachIndexed { x, point ->
                map.addPoint(Point(x, y, point.first()))
            }
        }
        return map
    }

    fun part1(input: List<String>): Int {
        val map = initHeightMap(input)
        val startingPoint = map.getStartingPoint()
        val bestSignal = map.getBestSignalPoint()
        return aStar(startingPoint, bestSignal, map)
    }

    fun part2(input: List<String>): Int {
        val map = initHeightMap(input)
        val possibleStartingPoints: List<Point> = map.getAllPointsOfElevation('a') + map.getStartingPoint()
        val startingPoints = possibleStartingPoints.filterNot { map.getNeighbours(it).all { p -> p.getElevation() == 'a' } }
        val bestSignal = map.getBestSignalPoint()
        return startingPoints.map { aStar(it, bestSignal, map) }.filter { it != 0 }.minOrNull()!!
    }

    val testInput = readInput("Day12_test", "y2022")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12", "y2022")
    println(part1(input))
    println(part2(input))
}
