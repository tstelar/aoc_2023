import Bag.Companion.bag
import Bag.Companion.emptyBag

data class Bag(val red: Int, val green: Int, val blue: Int) {
    companion object {
        val bag = Bag(red = 12, green = 13, blue = 14)
        val emptyBag = Bag(red = 0, green = 0, blue = 0)
    }

    fun drawnBag(encodedDraw: String) = this - decodeDraw(encodedDraw)

    fun getMinimumInBag(encodedDraw: String) = coerce(decodeDraw(encodedDraw))

    fun gameIsPossible() = red >= 0 && green >= 0 && blue >= 0

    fun getPower() = red * green * blue

    private fun coerce(draw: Bag) = Bag(
        red = red.coerceAtLeast(draw.red),
        green = green.coerceAtLeast(draw.green),
        blue = blue.coerceAtLeast(draw.blue)
    )

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

fun main() {
    fun getGameId(input: String) = input.substringAfter("Game ").substringBefore(":").toInt()

    fun getDraws(input: String) = input.split(": ")[1].split(";")

    fun part1(input: List<String>): Int = input.sumOf { line ->
        val gameId = getGameId(line)
        val draws = getDraws(line)
        val possibleDraws = draws.takeWhile {
            bag.drawnBag(it).gameIsPossible()
        }
        if (draws.size == possibleDraws.size) { gameId } else { 0 }
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        getDraws(line).fold(emptyBag) { bag, draw ->
            bag.getMinimumInBag(draw)
        }.getPower()
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

