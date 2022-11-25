import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var bestFuelUsed = -1

        for (targetPos in 0.. input.size) {
            var fuelUsed = 0

            for (pos in input) {
                val difference = abs(pos.toInt() - targetPos)
                fuelUsed += difference
            }

            if (bestFuelUsed == -1 ||  fuelUsed < bestFuelUsed) {
                bestFuelUsed = fuelUsed
            }
        }

        return bestFuelUsed
    }

    fun part2(input: List<String>): Int {
        var bestFuelUsed = -1

        for (targetPos in 0.. input.size) {
            var fuelUsed = 0

            for (pos in input) {
                var difference = 0
                for (i in 1 .. abs(pos.toInt() - targetPos))
                    difference += i
                fuelUsed += difference
            }

            if (bestFuelUsed == -1 ||  fuelUsed < bestFuelUsed) {
                bestFuelUsed = fuelUsed
            }
        }

        return bestFuelUsed
    }

    val testInput = readInput("Day07_test")[0].split(",")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")[0].split(",")
    println(part1(input))
    println(part2(input))
}