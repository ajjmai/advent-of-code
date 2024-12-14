package y2024

import readInput

fun main() {
    val gameRegex = """Button A: X\+(\d+), Y\+(\d+) Button B: X\+(\d+), Y\+(\d+) Prize: X=(\d+), Y=(\d+)""".toRegex()

    class ClawMachineGame(ax: Long, ay: Long, bx: Long, by: Long, px: Long, py: Long) {
        private val buttonA = Pair(ax, ay)
        private val buttonB = Pair(bx, by)
        private val prize = Pair(px, py)

        fun findPrize(limit: Int? = null): Long {
            val delta = buttonA.first * buttonB.second - buttonA.second * buttonB.first
            if (delta == 0L) return 0

            val numeratorA = (buttonB.second * prize.first - buttonB.first * prize.second)
            val numeratorB = (buttonA.first * prize.second - buttonA.second * prize.first)

            if (numeratorA % delta != 0L || numeratorB % delta != 0L) return 0

            val a = numeratorA / delta
            val b = numeratorB / delta

            return if (limit != null && (a !in 0..limit || b !in 0..limit)) 0 else a * 3 + b
        }
    }

    fun parseGames(input: List<String>, addExtra: Long = 0): List<ClawMachineGame> {
        val parsedInput = input.joinToString(" ").split("  ")

        return parsedInput.map { line ->
            val (ax, ay, bx, by, px, py) = gameRegex.find(line)!!.destructured
            ClawMachineGame(ax.toLong(), ay.toLong(), bx.toLong(), by.toLong(), px.toLong() + addExtra, py.toLong()+ addExtra)
        }
    }

    fun part1(input: List<String>): Long {
        return parseGames(input).sumOf { it.findPrize(100) }
    }

    fun part2(input: List<String>): Long {
        return parseGames(input, 10000000000000).sumOf { it.findPrize() }
    }

    val testInput = readInput("Day13_test", "y2024")
    check(part1(testInput) == 480L)
    check(part2(testInput) == 875318608908L)

    val input = readInput("Day13", "y2024")
    check(part1(input) == 30973L)
    check(part2(input) == 95688837203288L)
}
