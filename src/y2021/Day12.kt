package y2021

import readInput
import java.util.Stack

class Graph() {
    private val adjacencyMap: HashMap<String, HashSet<String>> = HashMap()

    fun init(input: List<String>) {
        for (line in input) {
            val parts = line.split('-')
            addEdge(parts[0], parts[1])
        }
    }

    private fun addEdge(sourceVertex: String, destinationVertex: String) {
        adjacencyMap
            .computeIfAbsent(sourceVertex) { HashSet() }
            .add(destinationVertex)

        if (sourceVertex != "start" && destinationVertex != "end") {
            adjacencyMap
                .computeIfAbsent(destinationVertex) { HashSet() }
                .add(sourceVertex)
        }
    }

    fun initVisitedMap() = mutableMapOf<String, Boolean>().apply {
        adjacencyMap.keys.forEach { put(it, false)}
    }

    fun getAdjancencyMap() = adjacencyMap

    override fun toString(): String = StringBuffer().apply {
        for (key in adjacencyMap.keys) {
            append("$key -> ")
            append(adjacencyMap[key]?.joinToString(", ", "[", "]\n"))
        }
    }.toString()
}

const val startNode = "start"
const val endNode = "end"

fun mayVisitMultipleTimes(node: String) = node.first().isUpperCase()

fun main() {
    fun part1(input: List<String>): Int {
        val paths = Graph()
        paths.init(input)
        println(paths.toString())

        val visitedMap = paths.initVisitedMap()
        println(visitedMap)
        val traversalList = mutableListOf<String>()
        println(traversalList)

        val stack = Stack<String>()
        stack.push(startNode)

        while (stack.isNotEmpty()) {
            val currentNode = stack.pop()
            println(currentNode)

            if (currentNode == endNode) {
                traversalList.add(currentNode)
                break
            }

            if (!visitedMap[currentNode]!!) {
                traversalList.add(currentNode)
                visitedMap[currentNode] = true

                paths.getAdjancencyMap()[currentNode]?.forEach { stack.push(it) }
            }
        }

        println(traversalList)


        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput1 = readInput("Day12_test1", "y2021")
    check(part1(testInput1) == 10)
    // check(part2(testInput1) == ??)

    // val testInput2 = readInput("Day12_test2")
    // check(part1(testInput2) == 19)
    //
    // val testInput3 = readInput("Day12_test3")
    // check(part1(testInput3) == 226)

    // val input = readInput("Day12")
    // println(part1(input))
    // println(part2(input))
}