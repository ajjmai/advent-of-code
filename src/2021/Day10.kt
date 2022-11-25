import java.util.Stack

private val opening = listOf<Char>('(', '[', '{', '<')

private val matchingBrackets = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
    ')' to '(',
    ']' to '[',
    '}' to '{',
    '>' to '<'
)

private val pointsForIncorrect = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

private val pointsForCompletion = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4
)

fun main() {
    fun part1(input: List<String>): Int {
        val stack = Stack<Char>()
        val incorrect: MutableList<Char> = mutableListOf()

        for (line in input) {
            for (char in line) {
                if (char in opening) {
                    stack.push(char)
                } else {
                    if (!stack.pop().equals(matchingBrackets[char])) {
                        incorrect.add(char)
                        stack.clear()
                        break
                    }
                }
            }
        }

        return incorrect.sumOf { pointsForIncorrect[it] ?: 0 }
    }

    fun part2(input: List<String>): Long {
        val stack = Stack<Char>()
        val completions: MutableList<String> = mutableListOf()

        for (line in input) {
            for (char in line) {
                if (char in opening) {
                    stack.push(char)
                } else {
                    if (!stack.pop().equals(matchingBrackets[char])) {
                        stack.clear()
                        break
                    }
                }
            }
            completions.add(stack.map { matchingBrackets[it] }.reversed().joinToString(""))
            stack.clear()
        }

        val scores: MutableList<Long> = mutableListOf()

        for (string in completions) {
            var score: Long  = 0
            for (s in string) {
                score = (score * 5) + (pointsForCompletion[s] ?: 0)
            }
            scores.add(score)
        }
        val filtered = scores.sorted().filter { it != 0L }

        return filtered[filtered.size / 2]
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}