fun main() {

    fun initBingoNumbers(input: List<String>) = input[0].split(',').map { it.toInt() }

    fun initBingoBoards(input: List<String>): Array<Array<Int>> {
        var boards = emptyArray<Array<Int>>()

        for (board in input.subList(1, input.size)) {
            if (board.isEmpty()) continue
            val row = board.replace("  ", " ").trim().split(' ').map { it.toInt() }
            boards += row.toTypedArray()
        }
        return boards
    }

    fun getColSum(boardNro: Int, col: Int, resultBoards: Array<IntArray>): Int {
        var colSum = 0
        for (row in (0 + boardNro * 5) until (5 + boardNro * 5)) {
            colSum += resultBoards[row][col]
        }
        return colSum
    }

    fun getUnmarkedSum(boardNro: Int, resultBoards: Array<IntArray>, boards: Array<Array<Int>>): Int {
        var unmarkedSum = 0

        for (row in (0 + boardNro * 5) until (5 + boardNro * 5)) {
            for (col in 0 until 5) {
                if (resultBoards[row][col] == 0) {
                    unmarkedSum += boards[row][col]
                }
            }
        }
        return unmarkedSum
    }

    fun part1(input: List<String>): Int {
        val bingoNumbers = initBingoNumbers(input)
        val boards = initBingoBoards(input)
        val numberOfBoards = boards.size / 5
        val resultBoards = Array(boards.size) {IntArray(5)}

        var winningBoard = -1
        var winningNumber = -1

        loop@ for (n in bingoNumbers) {
            for (row in boards.indices) {
                if (boards[row].contains(n)) {
                    val col = boards[row].indexOf(n)
                    resultBoards[row][col] = 1
                }
            }

            for (board in 0 until numberOfBoards) {
                for (row in (0 + board * 5) until (5 + board * 5)) {
                    for (col in 0 until 5) {
                        if (resultBoards[row].sum() == 5) {
                            winningBoard = board
                            winningNumber = n
                            break@loop
                        }
                    }
                }

                for (col in 0 until 5) {
                    if (getColSum(board, col, resultBoards) == 5) {
                        winningBoard = board
                        winningNumber = n
                        break@loop
                    }
                }

            }
        }

        return getUnmarkedSum(winningBoard, resultBoards, boards) * winningNumber
    }

    fun part2(input: List<String>): Int {
        val bingoNumbers = initBingoNumbers(input)
        val boards = initBingoBoards(input)
        val resultBoards = Array(boards.size) {IntArray(5)}
        val numberOfBoards = boards.size / 5

        var bingoBoard = -1
        var bingoNumber = -1
        var winningBoards = IntArray(numberOfBoards)

        loop@ for (n in bingoNumbers) {
            for (row in boards.indices) {
                if (boards[row].contains(n)) {
                    val col = boards[row].indexOf(n)
                    resultBoards[row][col] = 1
                }
            }

            for (board in 0 until numberOfBoards) {
                for (row in (0 + board * 5) until (5 + board * 5)) {
                    for (col in 0 until 5) {
                        if (resultBoards[row].sum() == 5) {
                            winningBoards[board] = 1
                            if (winningBoards.sum() == numberOfBoards) {
                                bingoBoard = board
                                bingoNumber = n
                                break@loop
                            }
                        }
                    }
                }

                for (col in 0 until 5) {
                    if (getColSum(board, col, resultBoards) == 5) {
                        winningBoards[board] = 1
                        if (winningBoards.sum() == numberOfBoards) {
                            bingoBoard = board
                            bingoNumber = n
                            break@loop
                        }
                    }
                }
            }
        }

        return getUnmarkedSum(bingoBoard, resultBoards, boards) * bingoNumber
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}