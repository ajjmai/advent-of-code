package `2022`

import readInput

// A, X = rock
// B, Y = paper
// C, Z = scissors
// X = 1, Y = 2, Z = 3
// 0 = lost, 3= draw, 6 = won

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach{
            val round = it.split(' ')
            if (round[1] == "X") { // rock
                score += 1
                when (round[0]) {
                    "A" -> score += 3 // draw
                    "B" -> score += 0 // paper beats rock
                    "C" -> score += 6 // scissors loses to rock
                }
            } else if (round[1] == "Y") { // paper
                score += 2
                when (round[0]) {
                    "A" -> score += 6 // rock loses to paper
                    "B" -> score += 3 // draw
                    "C" -> score += 0 // scissors beats paper
                }
            } else if (round[1] == "Z") { // scissors
                score += 3
                when (round[0]) {
                    "A" -> score += 0 // rock beats scissors
                    "B" -> score += 6 // paper loses to scissors
                    "C" -> score += 3 // draw
                }
            }
        }
        return score
    }

    // A = rock, B = paper, C = scissors
    // X = lose, Y = draw, Z = win
    // rock = 1, paper = 2, scissors = 3
    // 0 = lose, 3= draw, 6 = win

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach{
            val round = it.split(' ')
            if (round[1] == "X") { // lose
                score += 0
                when (round[0]) {
                    "A" -> score += 3 // rock beats scissors
                    "B" -> score += 1 // paper beats rock
                    "C" -> score += 2 // scissors beats paper
                }
            } else if (round[1] == "Y") { // draw
                score += 3
                when (round[0]) {
                    "A" -> score += 1 // rock
                    "B" -> score += 2 // paper
                    "C" -> score += 3 // scissors
                }
            } else if (round[1] == "Z") { // win
                score += 6
                when (round[0]) {
                    "A" -> score += 2 // rock loses to paper
                    "B" -> score += 3 // paper loses to scissors
                    "C" -> score += 1 // scissors loses to rock
                }
            }
        }
        return score
    }

    val testInput = readInput("Day02_test", "2022")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02", "2022")
    println(part1(input))
    println(part2(input))
}
