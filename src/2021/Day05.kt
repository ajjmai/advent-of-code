fun main() {

    fun getCoordinates(input: List<String>) = input.map { it.split(",", "->")}

    fun part1(input: List<String>): Int {
        val matrix = Array(1000) {IntArray(1000)}

        val coordinates = getCoordinates(input)

        for (item in coordinates) {
            val x1 = item[0].trim().toInt()
            val x2 = item[2].trim().toInt()
            val y1 = item[1].trim().toInt()
            val y2 = item[3].trim().toInt()

            if (x1 == x2) {
                if (y1 < y2) {
                    for (j in y1 .. y2) {
                        matrix[j][x1] = matrix[j][x1] + 1
                    }
                } else {
                    for (j in y1 downTo y2) {
                        matrix[j][x1] = matrix[j][x1] + 1
                    }
                }
            } else if (y1 == y2) {
                if (x1 < x2) {
                    for (i in x1 .. x2) {
                        matrix[y1][i] = matrix[y1][i] + 1
                    }
                } else {
                    for (i in x1 downTo x2) {
                        matrix[y1][i] = matrix[y1][i] + 1
                    }
                }
            }
        }

        var overlaps = 0
        matrix.forEach { it.forEach { x -> if(x > 1) overlaps++} }
        return overlaps
    }

    fun part2(input: List<String>): Int {
        val matrix = Array(1000) {IntArray(1000)}
        val coordinates = getCoordinates(input)

        for (item in coordinates) {
            val x1 = item[0].trim().toInt()
            val x2 = item[2].trim().toInt()
            val y1 = item[1].trim().toInt()
            val y2 = item[3].trim().toInt()

            if (x1 == x2) {
                if (y1 < y2) {
                    for (j in y1 .. y2) {
                        matrix[j][x1] = matrix[j][x1] + 1
                    }
                } else {
                    for (j in y1 downTo y2) {
                        matrix[j][x1] = matrix[j][x1] + 1
                    }
                }
            } else if (y1 == y2) {
                if (x1 < x2) {
                    for (i in x1 .. x2) {
                        matrix[y1][i] = matrix[y1][i] + 1
                    }
                } else {
                    for (i in x1 downTo x2) {
                        matrix[y1][i] = matrix[y1][i] + 1
                    }
                }
            } else {
                if (x1 < x2) { // (x1, y1 -> x2, y2)
                    if (y1 < y2) {
                        var j = y1
                        for (i in x1 .. x2) {
                            matrix[j][i] = matrix[j][i] + 1
                            j++
                        }
                    }
                    else  {
                        var j = y1
                        for (i in x1 .. x2) {
                            matrix[j][i] = matrix[j][i] + 1
                            j--
                        }
                    }
                }
                else {
                    if (y1 < y2) {
                        var j = y1
                        for (i in x1 downTo x2) {
                            matrix[j][i] = matrix[j][i] + 1
                            j++
                        }
                    }
                    else  {
                        var j = y1
                        for (i in x1 downTo x2) {
                            matrix[j][i] = matrix[j][i] + 1
                            j--
                        }
                    }
                }

            }
        }

        var overlaps = 0
        matrix.forEach { it.forEach { x -> if(x > 1) overlaps++} }

        return overlaps
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}