package dev.martinclaus.adventofcode.day1

/**
 * Something is wrong with global snow production, and you've been selected to take a look. The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.
 *
 * You've been doing this long enough to know that to restore snow operations, you need to check all fifty stars by December 25th.
 *
 * Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 *
 * You try to ask why they can't just use a weather machine ("not powerful enough") and where they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions") and hang on did you just say the sky ("of course, where do you think snow comes from") when you realize that the Elves are already loading you into a trebuchet ("please hold still, we need to strap you in").
 *
 * As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been amended by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading the values on the document.
 *
 * The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
 *
 * @see <a href="https://adventofcode.com/2018/day/1">Advent of Code 2018 Day 1</a>
 */
class PuzzleCalibrator {
    private val numbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    fun calibrate(puzzle: String): Int {
        return puzzle.lines().sumOf { input ->
            val firstDigit = getFirstDigit(replaceFromStart(input)).ifEmpty { "0" }
            val lastDigit = getFirstDigit(replaceFromEnd(input).reversed()).ifEmpty { "0" }
            (firstDigit + lastDigit).toInt()
        }
    }

    private fun getFirstDigit(line: String): String {
        return line.dropWhile { !it.isDigit() }.take(1)
    }

    private fun replaceFromStart(input: String): String {
        val firstMatch = numbers.entries
            .map { input.indexOf(it.key) to it }
            .filter { (index) -> index >= 0 }
            .minByOrNull { (index) -> index }?.second
        return input.replace(firstMatch?.key.orEmpty(), firstMatch?.value.toString())
    }

    private fun replaceFromEnd(input: String): String {
        val lastMatch = numbers.entries
            .map { input.lastIndexOf(it.key) to it }
            .maxByOrNull { (index) -> index }?.second
        return input.replace(lastMatch?.key.orEmpty(), lastMatch?.value.toString())
    }
}