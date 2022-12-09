package y2022

import readInput
import kotlin.math.abs

class KnotPosition {
    var x: Int = 0
    var y: Int = 0

    fun moveOne(direction:String) {
        when (direction) {
            "R" -> x += 1
            "L" -> x -= 1
            "U" -> y += 1
            "D" -> y -= 1
        }
    }

    fun moveNextTo(other: KnotPosition) {
        val xDirection = if (this.x < other.x) "R" else "L"
        val yDirection = if (this.y < other.y) "U" else "D"
        if (this.x == other.x) this.moveOne(yDirection)
        else if (this.y == other.y) this.moveOne(xDirection)
        else {
            this.moveOne(xDirection)
            this.moveOne(yDirection)
        }
    }

    fun isAdjacent(other: KnotPosition) =
        abs(this.x - other.x) <= 1 && abs(this.y - other.y) <= 1

}

fun main() {
    val directionRegex = "(\\w) (\\d+)".toRegex()

    fun part1(input: List<String>): Int {
        val head = KnotPosition()
        val tail = KnotPosition()
        val tailHistory = mutableListOf(Pair(0,0))
        input.forEach {
            val (direction, steps) = directionRegex.find(it)!!.destructured
            for (s in 0 until steps.toInt()) {
                head.moveOne(direction)
                if (!tail.isAdjacent(head)) tail.moveNextTo(head)
                tailHistory.add(Pair(tail.x, tail.y))
            }
        }
        return tailHistory.toSet().size
    }

    fun part2(input: List<String>): Int {
        val knots = List(10) { KnotPosition() }
        val tail = knots.last()
        val tailHistory = mutableListOf(Pair(0,0))

        input.forEach {
            val (direction, steps) = directionRegex.find(it)!!.destructured
            for (s in 0 until steps.toInt()) {
                knots.first().moveOne(direction)
                for (i in 1 until knots.size)
                    if (!knots[i].isAdjacent(knots[i - 1])) knots[i].moveNextTo(knots[i - 1])
                tailHistory.add(Pair(tail.x, tail.y))
            }
        }
        return tailHistory.toSet().size
    }

    val testInput = readInput("Day09_test", "y2022")
    // check(part1(testInput) == 13)
    check(part2(testInput) == 36)

    val input = readInput("Day09", "y2022")
    println(part1(input))
    println(part2(input))
}
