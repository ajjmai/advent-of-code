fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.reduce { prev, curr ->  if (curr.toInt() > prev.toInt()) result++; curr }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        var i = 1
        while (i < input.size - 2) {
            val window1 = input[i - 1].toInt() + input[i].toInt() + input[i + 1].toInt()
            val window2 = input[i].toInt() + input[i + 1].toInt() + input[i + 2].toInt()

            if (window1 < window2) result++

            i++
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)

    val input = readInput("day01")
    // println(part1(input))
    println(part2(input))
}
