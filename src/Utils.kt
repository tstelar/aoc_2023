import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

val digits = listOf(
    "0",
    "1",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine"
)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Retrieves the leftmost digit from a string.
 *
 * @return The leftmost digit as a string, or "0" if no digit is found.
 */
fun String.digitFromLeft() = matchAnyLeft(digits)?.toNumericRepresentation() ?: "0"

/**
 * Retrieves the rightmost digit from a string.
 *
 * This method searches for the rightmost occurrence of any digit character in the given string and
 * returns it as a string. If no digit is found, it returns "0".
 *
 * @return The rightmost digit as a string, or "0" if no digit is found.
 */
fun String.digitFromRight() = matchAnyRight(digits)?.toNumericRepresentation() ?: "0"

/**
 * Finds the first occurrence of any string in the given list in the input string and returns it.
 *
 * @param matches The list of strings to search for in the input string.
 * @return The first matching string found in the input string, or null if no matches are found.
 */
fun String.matchAnyLeft(matches: List<String>): String? = matches.associateBy {
    indexOf(it, ignoreCase = true)
}.toSortedMap()
    .filter {
        it.key != -1
    }.values
    .first()

/**
 * Returns the rightmost string in the list of matches which can be found in the given string.
 *
 * @param matches The list of strings to search for within the given string.
 * @return The rightmost string in the list of matches which is found in the given string, or null if no match is found.
 */
fun String.matchAnyRight(matches: List<String>): String? = matches.associateBy {
    lastIndexOf(it, ignoreCase = true)
}.toSortedMap()
    .values
    .last()

/**
 * Converts a string representation of a digit into its corresponding numeric representation.
 *
 * @return the numeric representation of the string digit, or the original string if no match is found.
 */
fun String.toNumericRepresentation() = when(this) {
    "one"   -> "1"
    "two"   -> "2"
    "three" -> "3"
    "four"  -> "4"
    "five"  -> "5"
    "six"   -> "6"
    "seven" -> "7"
    "eight" -> "8"
    "nine"  -> "9"
    else -> this
}

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
