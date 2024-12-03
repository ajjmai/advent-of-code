package y2024

import readInput

fun main() {
    val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    fun getSumOfMuls(input: List<String>): Int {
        return mulRegex.findAll(input.joinToString("")).sumOf { matchResult ->
            val (a, b) = matchResult.destructured
            a.toInt() * b.toInt()
        }
    }

    fun part1(input: List<String>): Int {
        return  getSumOfMuls(input)
    }

    fun part2(input: List<String>): Int {
        val parsedInput = input.joinToString("")
            .split(Regex("(?=don't())|(?=do())"))
            .filterNot { it.startsWith("don't()") }
        return getSumOfMuls(parsedInput)
    }

    val testInput = readInput("Day03_test", "y2024")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("Day03", "y2024")
    check(part1(input) == 189527826)
    check(part2(input) == 63013756)
}
