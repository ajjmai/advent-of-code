package y2021

import readInput

fun main() {
    fun getTargetCoordinates(input: List<String>): List<List<Int>> {
        val targetArea = input[0].substring(15).split("..", ", y=")
        val xTarget = targetArea.take(2).map { it.toInt() }
        val yTarget = targetArea.takeLast(2).map { it.toInt() }
        return listOf(xTarget, yTarget)
    }

    fun getXDrag(xPos: Int): Int {
        return if (xPos > 0) - 1 else if (xPos < 0) 1 else 0
    }

    fun isWithinTargetArea(xPos:Int, yPos:Int, targetArea: List<List<Int>>): Boolean {
        val xTarget = targetArea[0]
        val yTarget = targetArea[1]
        return xPos >= xTarget[0] && xPos <= xTarget[1] && yPos >= yTarget[0] && yPos <= yTarget[1]
    }

    fun isBeyondTargetArea(xPos:Int, yPos:Int, targetArea: List<List<Int>>): Boolean {
        val xTarget = targetArea[0]
        val yTarget = targetArea[1]
        return xPos > xTarget[1] || yPos > yTarget[1]
    }

    fun part1(input: List<String>): Int {
        val targetArea = getTargetCoordinates(input)
        var yOverallMax = 0

        for (initXVel in -500..500) {
            for (initYVel in -500..500) {
                var xPos = 0
                var yPos = 0
                var xVel = initXVel
                var yVel = initYVel
                var yMax = 0
                loop@ for (i in 0..500) {
                    xPos += xVel
                    yPos += yVel
                    xVel += getXDrag(xVel)
                    yVel -= 1
                    yMax = maxOf(yMax, yPos)
                    if (isWithinTargetArea(xPos, yPos, targetArea)) {
                        yOverallMax = maxOf(yOverallMax, yMax)
                        break@loop
                    }
                    if (isBeyondTargetArea(xPos, yPos, targetArea)) continue@loop
                }
            }
        }
        return yOverallMax
    }

    fun part2(input: List<String>): Int {
        val targetArea = getTargetCoordinates(input)
        val velocities: MutableList<List<Int>> = mutableListOf()

        for (initXVel in -500..500) {
            for (initYVel in -500..500) {
                var xPos = 0
                var yPos = 0
                var xVel = initXVel
                var yVel = initYVel
                loop@ for (i in 0..500) {
                    xPos += xVel
                    yPos += yVel
                    xVel += getXDrag(xVel)
                    yVel -= 1
                    if (isWithinTargetArea(xPos, yPos, targetArea)) {
                        velocities.add(listOf(initXVel, initYVel))
                        break@loop
                    }
                    if (isBeyondTargetArea(xPos, yPos, targetArea)) continue@loop
                }
            }
        }
        return velocities.size
    }

    val testInput = readInput("Day17_test", "y2021")
    check(part1(testInput) == 45)
    check(part2(testInput) == 112)

    val input = readInput("Day17", "y2021")
    println(part1(input))
    println(part2(input))
}