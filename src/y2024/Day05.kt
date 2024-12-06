package y2024

import readInput

fun main() {
    val ruleRegex = """(\d+)\|(\d+)""".toRegex()

    fun getRules(input: List<String>) = input.filter { it.matches(ruleRegex) }.map { it.split("|").map(String::toInt) }
    fun getUpdates(input: List<String>) = input.filterNot { it.matches(ruleRegex) }.drop(1).map { it.split(",").map(String::toInt) }

    fun splitToCorrectAndIncorrectUpdates(input: List<String>): Pair<List<List<Int>>, List<List<Int>>> {
        val correctUpdates: MutableList<List<Int>> = mutableListOf()
        val inCorrectUpdates: MutableList<List<Int>> = mutableListOf()

        for (update in getUpdates(input)) {
            val subLists = update.indices.map { update.subList(it, update.size) }
            val rulesForUpdate = update.flatMap { getRules(input).filter { rule -> rule.first() == it } }

            val rulesFromSublists = subLists.flatMap { subList ->
                val page = subList.first()
                subList.drop(1).map { listOf(page, it) }
            }
            if (rulesForUpdate.containsAll(rulesFromSublists)) {
                correctUpdates.add(update)
            } else {
                inCorrectUpdates.add(update)
            }
        }

        return Pair(correctUpdates, inCorrectUpdates)

    }

    fun part1(input: List<String>): Int {
        val (correctUpdates, _) = splitToCorrectAndIncorrectUpdates(input)
        return correctUpdates.sumOf { it[it.size /2] }
    }

    fun part2(input: List<String>): Int {
        val (_, inCorrectUpdates) = splitToCorrectAndIncorrectUpdates(input)

        return inCorrectUpdates.sumOf { update ->
            val rulesForUpdate = update.flatMap { getRules(input).filter { rule -> rule.first() == it } }

            val correctedUpdate = update.sortedWith { a, b ->
                when {
                    rulesForUpdate.find { it.first() == a && it.last() == b } == null -> 1
                    rulesForUpdate.find { it.first() == b && it.last() == a } == null -> -1
                    else -> 0
                }
            }
            correctedUpdate[correctedUpdate.size / 2]
        }
    }

    val testInput = readInput("Day05_test", "y2024")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05", "y2024")
    check(part1(input) == 5248)
    check(part2(input) == 4507)
}
