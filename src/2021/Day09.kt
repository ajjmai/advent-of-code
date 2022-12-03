fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.map { it.toList() }.map { it.map {x -> x.toString().toInt()} }

        var lowPointsSum = 0

        for (row in matrix.indices) {
            for (col in 0 until matrix[row].size) {
                val point = matrix[row][col]
                val north = matrix.getOrNull(row - 1)?.getOrNull(col)
                val south = matrix.getOrNull(row + 1)?.getOrNull(col)
                val west = matrix.getOrNull(row)?.getOrNull(col - 1)
                val east = matrix.getOrNull(row)?.getOrNull(col + 1)

                if ((north ?: 10) > point &&
                    (south ?: 10) > point &&
                    (west  ?: 10) > point &&
                    (east  ?: 10) > point
                ) {
                    lowPointsSum += (point + 1)
                }
            }
        }
        return lowPointsSum
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.toList() }.map { it.map {x -> x.toString().toInt()} }

        return 0
    }

    val testInput = readInput("Day09_test", "2021")
    check(part1(testInput) == 15)
    // check(part2(testInput) == 1134)

    val input = readInput("Day09", "2021")
    println(part1(input))
    // println(part2(input))
}