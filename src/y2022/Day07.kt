package y2022

import readInput
import kotlin.math.abs

class TreeNode(private val name: String, var size: Int? = null) {
    private val children: MutableList<TreeNode> = mutableListOf()
    private var parent: TreeNode? = null

    fun add(child: TreeNode) {
        children.add(child)
        child.parent = this
    }

    fun getSize(): Int {
        return if (isLeaf()) this.size ?: 0 else children.sumOf { it.getSize() }
    }

    private fun isLeaf() = children.isEmpty()

    fun getParent() = this.parent ?: this

    override fun toString(): String {
        return this.name
    }
}

fun main() {
    fun buildFileTree(input: List<String>): MutableList<TreeNode> {
        val fileRegex = "(\\d+) (\\w+.?\\w*)".toRegex()
        val dirRegex = "\\$ cd (\\w+)".toRegex()

        val root = TreeNode("/")
        val dirsList = mutableListOf(root)
        var previousDir: TreeNode
        var currentDir = root
        for (line in input) {
            if (line == "$ cd /") {
                previousDir = currentDir
                currentDir = root
            } else if (line == "$ cd ..") {
                previousDir = currentDir
                currentDir = previousDir.getParent()
            } else if (line.matches(dirRegex)) {
                val (name) = dirRegex.find(line)!!.destructured
                val dir = TreeNode("dir $name")
                dirsList.add(dir)
                previousDir = currentDir
                previousDir.add(dir)
                currentDir = dir
            } else if (line.matches(fileRegex)) {
                val (size, name) = fileRegex.find(line)!!.destructured
                currentDir.add(TreeNode(name, size.toInt()))
            }
        }
        return dirsList
    }

    fun part1(input: List<String>): Int {
        val dirs = buildFileTree(input)
        return dirs.map { it.getSize() }.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int? {
        val dirs = buildFileTree(input)
        val freeSizeNeeded = abs(70000000 - dirs.first().getSize() - 30000000)
        return dirs.map { it.getSize() }.filter { it >= freeSizeNeeded }.minOrNull()
    }

    val testInput = readInput("Day07_test", "y2022")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07", "y2022")
    println(part1(input))
    println(part2(input))
}
