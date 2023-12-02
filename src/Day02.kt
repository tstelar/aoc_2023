fun main() {
    data class Bag(val red: Int, val green: Int, val blue: Int) {
        fun draw(encodedDraw: String) = this - decodeDraw(encodedDraw)

        fun gameIsPossible() = red >= 0 && green >= 0 && blue >= 0

        private infix operator fun minus(other: Bag) = Bag(
            red = red - other.red,
            green = green - other.green,
            blue = blue - other.blue
        )

        private fun decodeDraw(encodedDraw: String): Bag {
            val draw = encodedDraw.split(", ").associate {
                val (count, color) = it.trim().split(" ")
                color to count.toInt()
            }
            return Bag(
                red = draw["red"] ?: 0,
                green = draw["green"] ?: 0,
                blue = draw["blue"] ?: 0
            )
        }
    }

    val bag = Bag(red = 12, green = 13, blue = 14)

    fun part1(input: List<String>): Int = input.sumOf { line ->
        val gameId = line.substringAfter("Game ").substringBefore(":").toInt()
        val draws = line.split(": ")[1].split(";")
        val possibleDraws = draws.takeWhile {
            bag.draw(it).gameIsPossible()
        }
        if (draws.size == possibleDraws.size) { gameId } else { 0 }
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        "${line.digitFromLeft()}${line.digitFromRight()}".toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
//    val testInput2 = readInput("Day02_test2")
//    check(part2(testInput2) == 281)

    val input = readInput("Day02")
    part1(input).println()
//    part2(input).println()
}

