package dev.martinclaus.adventofcode.day2

import dev.martinclaus.adventofcode.readLines
import kotlin.math.max

/**
 * @see <a href="https://adventofcode.com/2023/day/2">Advent of Code 2023 Day 2</a>
 */
fun main() {
    val input = "cube-conundrums.txt".readLines()

    println("Day 2: Cube Conundrum")
    println("Part I: What is the sum of the IDs of those games? ${partI(input, ConundrumSet(12, 13, 14))}")
    println("Part II: What is the sum of the power of these sets? ${partII(input)}")
}

fun partI(lines: List<String>, boxes: ConundrumSet): Int {
    val parsedConundrums = lines.map { parseConundrum(it) }
    return parsedConundrums.filter { validate(it.subsets, boxes) }.sumOf { it.id }
}

fun partII(lines: List<String>): Int = lines.map { parseConundrum(it) }.sumOf {
    val set = calculateMinimalSet(it.subsets)
    set.red * set.blue * set.green
}

private fun validate(subsets: List<ConundrumSet>, boxes: ConundrumSet): Boolean =
    calculateMinimalSet(subsets).let { minimalSet ->
        minimalSet.red <= boxes.red && minimalSet.green <= boxes.green && minimalSet.blue <= boxes.blue
    }

private fun parseConundrum(conundrum: String): Conundrum {
    val subsets = conundrum.substringAfter(":").split(";").map { subset ->
        subset.split((",")).fold(ConundrumSet(0, 0, 0)) { acc, color ->
            val (amount, colorName) = color.trim().split(" ")
            when (colorName) {
                "red" -> ConundrumSet(acc.red + amount.toInt(), acc.green, acc.blue)
                "green" -> ConundrumSet(acc.red, acc.green + amount.toInt(), acc.blue)
                "blue" -> ConundrumSet(acc.red, acc.green, acc.blue + amount.toInt())
                else -> acc
            }
        }
    }
    val id = conundrum.substringAfter("Game ").substringBefore(":").trim().toInt()
    return Conundrum(id, subsets)
}

private fun calculateMinimalSet(subsets: List<ConundrumSet>) = subsets.fold(ConundrumSet(0, 0, 0)) { acc, subset ->
    ConundrumSet(
        red = max(acc.red, subset.red),
        green = max(acc.green, subset.green),
        blue = max(acc.blue, subset.blue)
    )
}

data class ConundrumSet(
    val red: Int,
    val green: Int,
    val blue: Int
)

data class Conundrum(
    val id: Int,
    val subsets: List<ConundrumSet>
)