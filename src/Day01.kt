fun main() {
    fun part1(input: List<String>) = input.sumOf { line ->
        "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt()
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        "${line.digitFromLeft()}${line.digitFromRight()}".toInt()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

