package y2022

import readInput

enum class Order {
    CORRECT, INCORRECT, SAME
}

fun main() {

    fun comparePackets(left: Any , right: Any): Order {
        if (left == right) {
            return Order.SAME
        } else if (left is Int && right is Int) {
            return if (left < right) Order.CORRECT else Order.INCORRECT
        } else if (left is List<*> && right is Int) {
            return comparePackets(left, listOf(right))
        } else if (left is Int && right is List<*>) {
            return comparePackets(listOf(left), right)
        } else if (left is List<*> && right is List<*>) {
            for (i in left.indices) {
                if (i > right.size - 1) {
                    return Order.INCORRECT
                }
                val result = comparePackets(left[i]!!, right[i]!!)
                if (result in listOf(Order.CORRECT, Order.INCORRECT) ) return result
            }
        }
        return Order.CORRECT

    }

    fun parsePacket(packet: String, startIndex: Int = 0): Pair<List<Any>, Int> {
        val result = mutableListOf<Any>()
        var i = startIndex
        val currentNumber = StringBuilder()

        while (i <= packet.lastIndex) {
            when(val currentChar = packet[i]) {
                '[' -> {
                    val subPacket = parsePacket(packet, i + 1)
                    result.add(subPacket.first)
                    i = subPacket.second
                }
                ']' -> {
                    if (currentNumber.isNotEmpty()) result.add(currentNumber.toString().toInt())
                    return Pair(result, i + 1)
                }
                ',' -> {
                    if (currentNumber.isNotEmpty()) result.add(currentNumber.toString().toInt())
                    currentNumber.clear()
                    i += 1
                }
                else -> {
                    currentNumber.append(currentChar)
                    i += 1
                }
            }
        }
        if (currentNumber.isNotEmpty()) result.add(currentNumber.toString().toInt())
        return Pair(result, i)
    }

    fun parseInput(input: List<String>) =
        input.asSequence().filter { !it.isNullOrEmpty() }.map { parsePacket(it.removeSurrounding("[", "]")).first }.toList()


    fun part1(input: List<String>): Int {
        var sumOfCorrectOrders = 0
        val parsedInput = parseInput(input).chunked(2).map { Pair(it.first(), it.last()) }
        parsedInput.forEachIndexed { index, it ->
            val result = comparePackets(it.first, it.second)
            if (result == Order.CORRECT) {
                sumOfCorrectOrders += (index + 1)
            }
        }
        return sumOfCorrectOrders
    }

    fun part2(input: List<String>): Int {
        val dividerPacketOne = listOf(listOf(2))
        val dividerPacketTwo = listOf(listOf(6))
        val parsedInput = parseInput(input) + listOf(dividerPacketOne, dividerPacketTwo)
        val packetComparator = {first: Any, second: Any ->
            when(comparePackets(first, second)) {
                Order.CORRECT -> -1
                Order.SAME -> 0
                Order.INCORRECT -> 1
            }

        }
        val correctOrder = parsedInput.sortedWith(packetComparator)
        return (correctOrder.indexOf(dividerPacketOne) + 1) * (correctOrder.indexOf(dividerPacketTwo) + 1)
    }

    val testInput = readInput("Day13_test", "y2022")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day13", "y2022")
    println(part1(input))
    println(part2(input))
}
