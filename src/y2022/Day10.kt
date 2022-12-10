package y2022

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var x = 1
        val signalStrengths = mutableListOf<Int>()
        var cycle = 1

        input.forEach {
            if (cycle in listOf(20, 60, 100, 140, 180, 220)) {
                signalStrengths.add(cycle * x)
            }
            if ( it== "noop") {
                cycle += 1
            }
            else {
                val (_, V) = it.split(' ')
                cycle += 1
                if (cycle in listOf(20, 60, 100, 140, 180, 220)) {
                    signalStrengths.add(cycle * x)
                }
                cycle += 1
                x += V.toInt()
            }

        }
        return signalStrengths.sum()
    }

    fun part2(input: List<String>) {
        var x = 1
        var sprite = -1..1
        var cycle = 0
        var crtPixelPos = 0

        val crt = List(6) { MutableList(40) { "" } }

        input.forEach {
            crt[(cycle / 40)][crtPixelPos] = if (crtPixelPos in sprite) "#" else "."
            crtPixelPos = if (crtPixelPos == 39) 0 else  crtPixelPos + 1

            if ( it == "noop") {
                cycle += 1
            }
            else {
                val (_, V) = it.split(' ')
                cycle += 1

                crt[(cycle / 40)][crtPixelPos] = if (crtPixelPos in sprite) "#" else "."
                crtPixelPos = if (crtPixelPos == 39) 0 else  crtPixelPos + 1

                cycle += 1
                x += V.toInt()
                sprite = (x - 1)..(x + 1)
            }
        }

        crt.forEach { println(it.joinToString("")) }
    }

    val testInput = readInput("Day10_test", "y2022")
    check(part1(testInput) == 13140)
    part2(testInput)


    val input = readInput("Day10", "y2022")
    println(part1(input))
    part2(input)
}
