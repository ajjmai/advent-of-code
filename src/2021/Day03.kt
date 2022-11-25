fun main() {
    fun part1(input: List<String>): Int {
        var gamma = ""
        var epsilon = ""

        for ( i in 0 until input[0].length) {
            var zero = 0
            var one = 0
            input.forEach { if (it[i] == '0') zero++ else one++ }

            if (zero > one) {
                gamma += "0"
                epsilon += "1"
            } else {
                gamma += "1"
                epsilon += "0"
            }
        }

        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
    }

    fun part2(input: List<String>): Int {
        var oxygenGenerator = ""
        var co2Scrubber = ""

        var list = input

        for ( i in 0 until input[0].length) {

            var zero = 0
            var one = 0
            list.forEach { if (it[i] == '0') zero++ else one++ }

            if (zero > one) {
                list = list.filter { it[i] == '0' }
            } else {
                list = list.filter { it[i] == '1' }
            }

            if (list.size == 1) {
                oxygenGenerator = list[0]
                break
            }
        }

        list = input
        for ( i in 0 until input[0].length) {

            var zero = 0
            var one = 0
            list.forEach { if (it[i] == '0') zero++ else one++ }

            if (zero > one) {
                list = list.filter { it[i] == '1' }
            } else {
                list = list.filter { it[i] == '0' }
            }

            if (list.size == 1) {
                co2Scrubber = list[0]
                break
            }
        }

        return Integer.parseInt(oxygenGenerator, 2) * Integer.parseInt(co2Scrubber, 2)
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}