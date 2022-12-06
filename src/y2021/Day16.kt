package y2021

import readInput

// 0 = 0000
// 1 = 0001
// 2 = 0010
// 3 = 0011
// 4 = 0100
// 5 = 0101
// 6 = 0110
// 7 = 0111
// 8 = 1000
// 9 = 1001
// A = 1010 = 10
// B = 1011 = 11
// C = 1100 = 12
// D = 1101 = 13
// E = 1110 = 14
// F = 1111 = 15

fun hexToBinary(char: Char) =
    char.toString().toInt(radix = 16).toString(2).padStart(4, '0')

fun binaryToInt(string: String) =
    string.toInt(2)

fun parseLiteraryString(string: String): Int {
    // var length = 0
    // var startIndex = 6
    // while (true) {
    //     val group = string.substring(startIndex, startIndex + 5)
    //     length += 5
    //     startIndex += 5
    //
    //     if (group[0] == '0') {
    //         length += length.rem(4)
    //         break
    //     }
    // }

    return binaryToInt(string.take(3))
}

fun parsePacket(string: String): Int {
    val typeId = string.substring(3, 6)
    if (binaryToInt(typeId) != 4) {
        val lengthTypeId = string.substring(6, 7)
        if (lengthTypeId == "0") {
            val start = 7
            val end = start + 15
            val totalLength = binaryToInt(string.substring(start, end))

            parsePacket(string.substring(end, end + totalLength))
        } else {
            val start = 7
            val end = start + 11
            val numberOfSubPackets = binaryToInt(string.substring(start, end))

            println(numberOfSubPackets)
        }

    } else {
        val version = string.take(3)
        return binaryToInt(version)
    }
    return 0
}


fun main() {
    fun part1(input: List<String>): Int {
        val list = input[0].toCharArray().toList()
        val binaryString = list.joinToString("") { hexToBinary(it) }
        println(binaryString)

        var versionSum = 0
        var packageIndex = 0

        val version = binaryString.take(3)
        val typeId = binaryString.substring(3, 6)

        println("$version = ${binaryToInt(version)}")
        println("$typeId = ${binaryToInt(typeId)}")

        if (binaryToInt(typeId) != 4) {
            val lengthTypeId = binaryString.substring(6, 7)

            if (lengthTypeId == "0") {
                val start = packageIndex + 7
                val end = start + 15
                val totalLength = binaryToInt(binaryString.substring(start, end))
                val substrings = binaryString.substring(end, end + totalLength)
                println(substrings)

            } else {
                val start = packageIndex + 7
                val end = start + 11
                val numberOfSubPackets = binaryToInt(binaryString.substring(start, end))
                println(numberOfSubPackets)
            }

        } else {
            val start = 0
            val end = 11
            versionSum += parseLiteraryString(binaryString.substring(start, end))
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day16_test", "y2021")
    check(part1(testInput) == 16)
    // check(part2(testInput) == ??)

    val input = readInput("Day16", "y2021")
    // println(part1(input))
    // println(part2(input))
}