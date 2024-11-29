package y2023

import readInput

fun main() {

    val digits = mapOf("one" to "o1e", "two" to "t2o", "three" to "t3e", "four" to "f4r", "five" to "f5e", "six" to "s6x", "seven" to "s7n", "eight" to "e8t", "nine" to "n9e")

    fun calibrate(input: List<String>): List<Int> {
        return input.map { line ->
            val numbers = line.filter { it.isDigit() }
            when {
                numbers.length == 1 -> "${numbers[0]}${numbers[0]}".toInt()
                numbers.length > 2 -> "${numbers.first()}${numbers.last()}".toInt()
                else -> numbers.toInt()
            }
        }
    }

    fun replaceDigits(input: String): String {
        val regex = Regex(digits.keys.joinToString("|"))
        return regex.replace(input) { matchResult ->
            digits[matchResult.value] ?: matchResult.value
        }
    }

    fun calibrate2(input: List<String>): List<Int> {
        return calibrate(input.map { replaceDigits(it) }.map { replaceDigits(it) })
    }

    fun part1(input: List<String>): Int {
        val values = calibrate(input)
        return values.sum()
    }

    fun part2(input: List<String>): Int {
        val values = calibrate2(input)
        return values.sum()
    }

    val testInput = readInput("Day01_test", "y2023")
//    check(part1(testInput) == 142)
    check(part2(testInput) == 309) //281+28 = 309

    val input = readInput("Day01", "y2023")
    println(part1(input))  // 55971
    println(part2(input)) // 54719
}
