import java.util.Collections

fun main() {
    fun part1(input: List<String>): Int {
        var fish = input.map { it.toInt() }.toMutableList()
        val days = 80

        for (i in 0 until days) {
            var newFish = 0
            for (i in fish.indices) {
                if (fish[i] == 0) {
                    fish[i] = 6
                    newFish++
                } else {
                    fish[i] = fish[i] - 1
                }
            }
            var newFishList = IntArray(newFish) {8}.toMutableList()

            fish = (fish + newFishList) as MutableList<Int>
        }
        return fish.size
    }

    fun part2(input: List<String>): Long {
        var counts = mutableListOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0)
        input.forEach { counts[it.toInt()] = counts[it.toInt()] + 1 }

        for (d in 0 until 256 ) {
            val temp = counts[0]
            for (i in 1 .. 8) {
                counts[i - 1] = counts[i]
            }
            counts[8] = temp
            counts[6] = counts[6] + temp
        }

        return counts.sum()
    }

    val testInput = readInput("Day06_test")[0].split(",")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")[0].split(",")
    println(part1(input))
    println(part2(input))
}