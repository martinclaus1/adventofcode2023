package dev.martinclaus.adventofcode.day3

import dev.martinclaus.adventofcode.AdventOfCodeTask

/**
 * @see <a href="https://adventofcode.com/2023/day/3">Advent of Code 2023 Day 3</a>
 */
class GearRatios: AdventOfCodeTask {
    private val numberPattern = "\\d+".toRegex().toPattern()
    private val charPattern = "[^\\.0-9]".toRegex().toPattern()
    private val asteriskPattern = "\\*".toRegex().toPattern()

    override fun solve() {
        val schematic = this::class.java.classLoader.getResource("engine-schematic.txt")?.readText().orEmpty()
        println("Day 3: Gear Ratios")
        println("\tPart I: What is the sum of all of the part numbers in the engine schematic? ${sumAllPartNumbers(schematic)}")
        println("\tPart II: What is the sum of all of the gear ratios in your engine schematic? ${sumGears(schematic)}")
    }

    fun sumAllPartNumbers(schematic: String): Int {
        val lines = schematic.lines()
        val numbers = getNumbers(lines)
        val chars = getCharacters(lines)

        val result = numbers.mapIndexed { index, row ->
            val charsAbove = if (index > 0) chars[index - 1] else emptySet()
            val charsBesides = chars[index]
            val charsBelow = if (index < chars.size - 1) chars[index + 1] else emptySet()

            row.filter { number ->
                val range = number.start - 1..number.end
                isAdjacent(range, charsAbove, charsBesides, charsBelow)
            }.sumOf { it.value }
        }.sum()

        return result
    }

    fun sumGears(schematic: String): Int {
        val lines = schematic.lines()
        val numbers = getNumbers(lines)
        val asterisks = lines.map { line ->
            asteriskPattern.matcher(line).results().map { it.start() }.toList().toSet()
        }

        return asterisks.mapIndexed { index, row ->
            val numbersAbove = if (index > 0) numbers[index - 1] else emptySet()
            val numbersBesides = numbers[index]
            val numbersBelow = if (index < numbers.size - 1) numbers[index + 1] else emptySet()

            getGears(row, numbersAbove, numbersBesides, numbersBelow).sumOf { it.reduce(Int::times) }
        }.sum()
    }

    /**
     * a gear is an asterisk surrounded by two numbers
     */
    private fun getGears(
        row: Set<Int>,
        numbersAbove: Set<Number>,
        numbersBesides: Set<Number>,
        numbersBelow: Set<Number>
    ) = row.map {
        val range = it - 1..it + 1
        getNumbersAround(numbersAbove, range) + getNumbersAround(numbersBesides, range) + getNumbersAround(
            numbersBelow,
            range
        )
    }.filter { it.size == 2 }

    private fun isAdjacent(
        range: IntRange,
        charsAbove: Set<Int>,
        charsBesides: Set<Int>,
        charsBelow: Set<Int>
    ) = hasCharAround(charsAbove, range) || hasCharAround(charsBesides, range) || hasCharAround(
        charsBelow,
        range
    )

    private fun getCharacters(lines: List<String>) = lines.map { line ->
        charPattern.matcher(line).results().map { it.start() }.toList().toSet()
    }

    private fun getNumbers(lines: List<String>) = lines.map { line ->
        numberPattern.matcher(line).results().map { Number(it.start(), it.end(), it.group().toInt()) }.toList()
            .toSet()
    }

    private fun hasCharAround(chars: Set<Int>, range: IntRange): Boolean {
        return chars.any { it in range }
    }

    private fun getNumbersAround(numbers: Set<Number>, range: IntRange): Set<Int> {
        return numbers.filter { it.start in range || it.end - 1 in range }.map { it.value }.toSet()
    }
}

data class Number(
    val start: Int, // inclusive
    val end: Int, // exclusive
    val value: Int
)