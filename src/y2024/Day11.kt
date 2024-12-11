package y2024

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var stones = input.first().split(" ")

        repeat(25) {
            stones = stones.flatMap { stone ->
                val digits = stone.length
                when {
                    stone == "0" -> listOf("1")
                    digits % 2 == 0 -> listOf(
                        stone.substring(0, digits / 2),
                        stone.substring(digits / 2).trimStart { it == '0' }.ifEmpty { "0" })

                    else -> listOf((stone.toLong() * 2024).toString())
                }
            }
        }
        return stones.size
    }

    fun part2(input: List<String>): Long {
        var stones = input.first().split(" ").map(String::toLong).associateWith { 1L } as HashMap<Long, Long>

        repeat(75) {
            val blinked: HashMap<Long, Long> = hashMapOf()
            for ((stone, count) in stones) {
                val stoneAsString = stone.toString()
                val digits = stoneAsString.length
                when {
                    stone == 0L -> blinked[1] = blinked.getOrDefault(1, 0) + count
                    digits % 2 == 0 -> {
                        val leftPart = stoneAsString.substring(0, digits / 2).toLong()
                        val rightPart = stoneAsString.substring(digits / 2).toLong()
                        blinked[leftPart] = blinked.getOrDefault(leftPart, 0) + count
                        blinked[rightPart] = blinked.getOrDefault(rightPart, 0) + count
                    }
                    else -> blinked[stone * 2024] = blinked.getOrDefault(stone * 2024, 0) + count
                }
            }
            stones = blinked
        }
        return stones.values.sum()
    }

    val testInput = readInput("Day11_test", "y2024")
    check(part1(testInput) == 55312)
    check(part2(testInput) == 65601038650482L)

    val input = readInput("Day11", "y2024")
    check(part1(input) == 217812)
    check(part2(input) == 259112729857522L)
}
