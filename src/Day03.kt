fun main() {

    val markers = setOf('#', '&', '+', '-', '$', '%', '@', '!', '?', '*', '/', '=')

    data class Engine(
        val len: Int,
        val numbers: Set<Int> = setOf(),
        val symbols: Set<Int> = setOf()
    ) {
        fun sumParts(input: String) = symbols.flatMap {
                val left = it - 1
                val right = it + 1
                val up = it - len
                val down = it + len
                val diagonalUpLeft = up - 1
                val diagonalUpRight = up + 1
                val diagonalDownLeft = down - 1
                val diagonalDownRight = down + 1

                val extracted = mutableListOf<Int>()
                if (contains(up)) {
                    extracted.add(extractNumber(input, up).toInt())
                } else {
                    if (contains(diagonalUpLeft)) extracted.add(extractNumberLeft(input, diagonalUpLeft).toInt())
                    if (contains(diagonalUpRight)) extracted.add(extractNumberRight(input, diagonalUpRight).toInt())
                }
                if (contains(down)) {
                    extracted.add(extractNumber(input, down).toInt())
                } else {
                    if (contains(diagonalDownLeft)) extracted.add(extractNumberLeft(input, diagonalDownLeft).toInt())
                    if (contains(diagonalDownRight)) extracted.add(extractNumberRight(input, diagonalDownRight).toInt())
                }
                if (contains(left)) { extracted.add(extractNumberLeft(input, left).toInt()) }
                if (contains(right)) { extracted.add(extractNumberRight(input, right).toInt()) }
                extracted
            }.sum()

        private fun contains(idx: Int) = numbers.contains(idx)

        fun extractNumber(input: String, index: Int) =
            extractNumberLeft(input, index) + extractNumberRightExclusive(input, index)

        fun extractNumberLeft(input: String, index: Int) =
            input.substring(
                if (index - 3 < 0) 0 else index - 3,
                index + 1
            ).reversed()
            .takeWhile(Char::isDigit)
            .reversed()

        fun extractNumberRight(input: String, index: Int) =
            input.substring(index).takeWhile(Char::isDigit)

        fun extractNumberRightExclusive(input: String, index: Int) =
            input.substring(index + 1).takeWhile(Char::isDigit)
    }


    fun part1(input: List<String>) = input.foldIndexed(Engine(input.first().length)) { lineIdx, acc, current ->
        val globalIdx = lineIdx * acc.len
        current.foldIndexed(acc) { idx, acc2, symbol ->
            val globalLineIdx = globalIdx + idx
            if (symbol.isDigit()) {
                acc2.copy(numbers = acc2.numbers + globalLineIdx)
            } else if (markers.contains(symbol)) {
                acc2.copy(symbols = acc2.symbols + globalLineIdx)
            } else {
                acc2
            }
        }
    }.sumParts(input.joinToString(""))

    fun part2(input: List<String>) = input.sumOf { line ->
        "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt()
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    val testInput2 = readInput("Day01_test2")
//    check(part2(testInputnput2) == 281)

    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}

