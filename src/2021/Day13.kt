data class Dot(val x: Int, val y: Int)

fun main() {
    fun part1(input: List<String>): Int {
        val parts = input.partition { "fold" in it }
        val folds = parts.first

        val dots: MutableList<Dot> = mutableListOf()
        for (item in parts.second) {
            if (item.isEmpty()) continue
            val coordinates = item.split(',')
            dots.add(Dot(coordinates[0].toInt(), coordinates[1].toInt()))
        }

        val grid = Array(dots.maxOf { it.y + 1 }) { IntArray(dots.maxOf { it.x + 1 }) }

        for (dot in dots) {
            grid[dot.y][dot.x] = 1
        }

        var foldLine = folds[0].substringAfter('=').toInt()

        val foldedGrid: List<IntArray> = grid.map { it.slice(0 until foldLine).toIntArray() }

        for ((x1, x2) in (grid[0].size - 1 downTo foldLine + 1).withIndex()) {
            for (y in grid.indices) {
                if (grid[y][x2] == 1) {
                    foldedGrid[y][x1] = 1
                }
            }
        }

        return foldedGrid.sumOf { it.sum() }
    }

    fun part2(input: List<String>) {
        val parts = input.partition { "fold" in it}
        val folds = parts.first

        val dots: MutableList<Dot> = mutableListOf()
        for (item in parts.second) {
            if (item.isEmpty()) continue
            val coordinates = item.split(',')
            dots.add(Dot(coordinates[0].toInt(), coordinates[1].toInt()))
        }

        val grid = Array(dots.maxOf { it.y + 1 }) {IntArray(dots.maxOf { it.x + 1 })}

        for (dot in dots) {
            grid[dot.y][dot.x] = 1
        }

        var gridToFold = grid

        for (fold in folds) {
            var foldLine = fold.substringAfter('=').toInt()
            var foldDirection = fold.findLast { it=='x' || it=='y' }

            if (foldDirection == 'y') {
                val foldedGrid = gridToFold.slice(0 until foldLine).toTypedArray()

                for ((y1, y2) in (gridToFold.size - 1 downTo foldLine + 1).withIndex()) {
                    for (x in 0 until gridToFold[0].size) {
                        if (gridToFold[y2][x] == 1) {
                            foldedGrid[y1][x] = 1
                        }
                    }
                }
                gridToFold = foldedGrid
            }

            if (foldDirection == 'x') {
                val foldedGrid = gridToFold.map {  it.slice(0 until foldLine).toIntArray()}.toTypedArray()

                for ((x1, x2) in (gridToFold[0].size - 1 downTo foldLine + 1).withIndex()) {
                    for (y in gridToFold.indices) {
                        if (gridToFold[y][x2] == 1) {
                            foldedGrid[y][x1] = 1
                        }
                    }
                }
                gridToFold = foldedGrid
            }
        }
        gridToFold.forEach { println(it.contentToString().replace('0', ' ').replace('1', '#')) }
    }

    val testInput = readInput("Day13_test", "2021")
    check(part1(testInput) == 17)
    // check(part2(testInput) == 17)

    val input = readInput("Day13", "2021")
    println(part1(input))
    part2(input)
}