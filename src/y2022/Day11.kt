package y2022

import readInput

class Monkey(
    private var items: MutableList<Long>,
    private val worryOperator: String,
    private val worryNumber: String,
    val divider: Long,
    private val trueCondition: Int,
    private val falseCondition: Int
) {
    var totalItemsInspected = 0L
    private var modulo = 0L

    private fun addItem(item: Long) = this.items.add(item)

    fun addModulo(modulo: Long) {this.modulo = modulo}

    fun inspectItemsAndThrowToNext(monkeys: List<Monkey>) = run {
        for (item in items) {
            val updatedItem = this.calculateWorry(item)
            val nextMonkeyName = getNextMonkey(updatedItem)
            monkeys[nextMonkeyName].addItem(updatedItem)
        }
        totalItemsInspected += this.items.size
        this.items = mutableListOf()
    }

    private fun calculateWorry(item: Long): Long {
        val toBeAdded = if (this.worryNumber == "old") item else this.worryNumber.toLong()
        val increased = if (this.worryOperator == "+") item.plus(toBeAdded) else item.times(toBeAdded)
        // val decreased = increased / 3L
        // return decreased
        return increased % this.modulo
    }

    private fun getNextMonkey(item: Long) = if (item % this.divider == 0L) this.trueCondition else this.falseCondition
}

fun initMonkeys(input: List<String>): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()

    input.filter { !it.isNullOrEmpty() }.chunked(6).forEach { monkey ->
        var startingItems: List<Long> = monkey.find { it.contains("Starting items") }?.substringAfter(": ")?.split(", ")?.map{ item -> item.toLong()}!!
        val operation: List<String> = monkey.find { it.contains("Operation") }?.substringAfter("Operation: new = old ")?.split(' ')!!
        var testDivision = monkey.find { it.contains("Test") }?.substringAfter("by ")?.toLong()!!
        var trueCondition = monkey.find { it.contains("true") }?.substringAfter("monkey ")?.trim()?.toInt()!!
        var falseCondition = monkey.find { it.contains("false") }?.substringAfter("monkey ")?.trim()?.toInt()!!

        monkeys.add(Monkey(startingItems.toMutableList(), operation.first(), operation.last(), testDivision ,trueCondition, falseCondition ))
    }
    val modulo = monkeys.map { it.divider }.reduce { acc, l -> acc * l }
    monkeys.forEach { it.addModulo(modulo) }

    return monkeys
}

fun main() {
    fun part1(input: List<String>): Long {
        val monkeys = initMonkeys(input)
        for (i in  1..20) {
            monkeys.forEach { it.inspectItemsAndThrowToNext(monkeys) }
        }
        return monkeys.map { it.totalItemsInspected }.sortedDescending().take(2).reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val monkeys = initMonkeys(input)
        for (i in  1..10000) {
            monkeys.forEach { it.inspectItemsAndThrowToNext(monkeys) }
        }
        return monkeys.map { it.totalItemsInspected }.sortedDescending().take(2).reduce { acc, i -> acc * i }
    }

    val testInput = readInput("Day11_test", "y2022")
    // check(part1(testInput) == 10605L)
    check(part1(testInput) == 10197L) // no division by 3
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11", "y2022")
    println(part1(input))
    println(part2(input))
}