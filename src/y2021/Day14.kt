fun String.addCharAtIndex(char: Char, i: Int) =
    StringBuilder(this).apply {insert(i, char) }.toString()

fun main() {
    fun part1(input: List<String>): Int {
        var template = input[0]

        val insertions = input.drop(2).associate {
            it.substringBefore(" -> ") to it.substringAfter(" -> ")
        }

        for (i in 0..9) {
            val matches = insertions.filter { it.key in template }
            var thisRoundInsertions = mutableMapOf<Int, String>()

            for (match in matches) {
                var index = -1
                while (true) {
                    index = template.indexOf(match.key, index + 1)
                    if (index == -1) break
                    thisRoundInsertions[index + 1] = match.value
                }
            }

            thisRoundInsertions = thisRoundInsertions.toSortedMap()

            var charsAdded = 0

            for (item in thisRoundInsertions) {
                template = template.addCharAtIndex(item.value[0], item.key + charsAdded)
                charsAdded++
            }
        }

        val counts = template.toCharArray().groupBy { it }.map { it.value.size }.toList()

        return counts.maxOf { it } - counts.minOf { it }
    }

    fun part2(input: List<String>): Long {
        var template = input[0]

        val insertions = input.drop(2).associate {
            it.substringBefore(" -> ") to it.substringAfter(" -> ")
        }

        for (i in 0..39) {
            val matches = insertions.filter { it.key in template }
            var thisRoundInsertions = mutableMapOf<Int, String>()

            for (match in matches) {
                var index = -1
                while (true) {
                    index = template.indexOf(match.key, index + 1)
                    if (index == -1) break
                    thisRoundInsertions[index + 1] = match.value
                }
            }

            thisRoundInsertions = thisRoundInsertions.toSortedMap()

            var charsAdded = 0

            for (item in thisRoundInsertions) {
                template = template.addCharAtIndex(item.value[0], item.key + charsAdded)
                charsAdded++
            }
        }

        val counts = template.toCharArray().groupBy { it }.map { it.value.size }.toList()

        return counts.maxOf { it } - counts.minOf { it }.toLong()
    }

    val testInput = readInput("Day14_test", "y2021")
    // check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14", "y2021")
    // println(part1(input))
    // println(part2(input))
}