package `2022`
import java.util.Stack
import readInput

class CargoStack(crates: String) {
    private val stack: Stack<Char> = Stack()

    init {
        for (c in crates) {
            stack.push(c)
        }
    }

    fun push(crate:Char): Char = stack.push(crate)

    fun pop(): Char = stack.pop()

    fun peek(): Char = stack.peek()
}

fun getCargoCraneProcedure(move: String): List<Int> {
    val numberOfCratesToMove = move.substringBefore(" from").substringAfter("move ").toInt()
    val from = move.substringBefore(" to").substringAfter("from ").toInt()
    val to = move.substringAfter("to ").toInt()
    return listOf(numberOfCratesToMove, from - 1, to - 1)
}

fun getMoves(input: List<String>) = input.joinToString().split(", , ")[1].split(", ")

fun main() {
    val testCargoStacks = listOf(
        CargoStack("ZN"),
        CargoStack("MCD"),
        CargoStack("P"))

    val cargoStacks = listOf(
        CargoStack("SLW"),
        CargoStack("JTNQ"),
        CargoStack("SCHFJ"),
        CargoStack("TRMWNGB"),
        CargoStack("TRLSDHQB"),
        CargoStack("MJBVFHRL"),
        CargoStack("DWRNJM"),
        CargoStack("BZTFHNDJ"),
        CargoStack("HLQNBFT"),
    )

    val testCargoLists = mutableListOf("ZN", "MCD", "P")

    val cargoLists = mutableListOf(
        "SLW",
        "JTNQ",
        "SCHFJ",
        "TRMWNGB",
        "TRLSDHQB",
        "MJBVFHRL",
        "DWRNJM",
        "BZTFHNDJ",
        "HLQNBFT",
    )

    fun part1(input: List<String>, cargoStacks: List<CargoStack>): String {
        val moves = getMoves(input)
        moves.forEach {
            val procedure = getCargoCraneProcedure(it)
            val numberOfCrates = procedure[0]
            for (i in 1..numberOfCrates) {
                val crate = cargoStacks[procedure[1]].pop()
                cargoStacks[procedure[2]].push(crate)
            }
        }
        return cargoStacks.map { it.peek() }.joinToString(separator = "")
    }

    fun part2(input: List<String>, cargoLists: MutableList<String>): String {
        val moves = getMoves(input)
        moves.forEach {
            val procedure = getCargoCraneProcedure(it)
            val numberOfCrates = procedure[0]
            val from = procedure[1]
            val to = procedure[2]
            val crates = cargoLists[from].takeLast(numberOfCrates)
            cargoLists[from] = cargoLists[from].removeSuffix(crates)
            cargoLists[to] = cargoLists[to] + crates
        }
        return cargoLists.map { it.last() }.joinToString(separator = "")
    }

    val testInput = readInput("Day05_test", "2022")
    check(part1(testInput,testCargoStacks) == "CMZ")
    check(part2(testInput, testCargoLists) == "MCD")

    val input = readInput("Day05", "2022")
    println(part1(input, cargoStacks))
    println(part2(input, cargoLists))
}
