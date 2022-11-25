val forward = "forward"
val down = "down"
val up = "up"

fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        for (item in input) {
            val parts = item.split(" ")

            when (parts[0]) {
                forward -> horizontal += parts[1].toInt()
                down -> depth += parts[1].toInt()
                up -> depth -= parts[1].toInt()
            }
        }

        println(horizontal)
        println(depth)

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        for (item in input) {
            val parts = item.split(" ")

            when (parts[0]) {
                forward -> {
                    horizontal += parts[1].toInt()
                    depth += aim * parts[1].toInt()
                }
                down -> aim += parts[1].toInt()
                up -> aim -= parts[1].toInt()
            }
        }

        println(horizontal)
        println(depth)

        return horizontal * depth
    }

    val input = readInput("Day02")
    // println(part1(input))
    println(part2(input))
}