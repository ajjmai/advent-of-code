fun main() {
    fun part1(input: List<String>): Int {
        val outputs = input.map { it.substringAfter('|') }.map { it.trim().split(' ') }

        var digits = 0
        outputs.forEach { it.forEach { x -> if (x.length in listOf<Int>(2, 3, 4, 7)) digits++ } }

        return digits
    }

    fun sortAlphabetically(string: String) = string.toCharArray().sorted().joinToString("")

    fun getValue(list: MutableList<String>, digitToCompare: String) =
        list.find { it.toList().containsAll(digitToCompare.toList()) }.toString()


    fun findItemOfLength(list: List<String>, len: Int) = list.find { it.length == len }.toString()

    fun findItemsOfLength(list: List<String>, len: Int) = list.filter { it.length == len }.distinct().toMutableList()

    fun part2(input: List<String>): Int {

        var result = 0

        for (line in input) {
            val parts = line.split(" | ", " ").map { sortAlphabetically(it) }

            val signals = parts.take(10)
            val outputs = parts.takeLast(4)

            val one = findItemOfLength(signals, 2)
            val four = findItemOfLength(signals, 4)
            val seven = findItemOfLength(signals, 3)
            val eight = findItemOfLength(signals, 7)

            var sixSegmentValues = findItemsOfLength(signals, 6)
            val nine = getValue(sixSegmentValues, four)
            sixSegmentValues.remove(nine)
            val zero = getValue(sixSegmentValues, seven)
            sixSegmentValues.remove(zero)
            val six = sixSegmentValues[0]


            var fiveSegmentValues = findItemsOfLength(signals, 5)
            val three = getValue(fiveSegmentValues, seven)
            fiveSegmentValues.remove(three)
            val five = getValue(fiveSegmentValues, nine!!.toList().intersect(six!!.toSet()).joinToString(""))
            fiveSegmentValues.remove(five)
            val two = fiveSegmentValues[0]

            val digits = listOf<String>(zero, one, two, three, four, five, six, seven, eight, nine )

            var fourDigitValue = ""

            for (item in outputs) {
                fourDigitValue += digits.indexOf(item)
            }
            result += fourDigitValue.toInt()
        }

        return result
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}