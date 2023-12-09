package dev.martinclaus.adventofcode.day4

import dev.martinclaus.adventofcode.printSolution
import dev.martinclaus.adventofcode.readLines
import kotlin.math.pow


/**
 * @see <a href="https://adventofcode.com/2023/day/4">Advent of Code 2023 Day 4</a>
 */
fun main() {
    val input = "scratchcards.txt".readLines()
    printSolution(
        4,
        "Scratchcards",
        "How many points are they worth in total? ${partI(input)}",
        "How many total scratchcards do you end up with? ${partII(input)}",
    )
}

fun partI(lines: List<String>): Int {
    return lines.sumOf { line ->
        val matches = getMatches(line)
        2.0.pow(matches - 1.0).toInt()
    }
}

fun partII(lines: List<String>): Int {
    val cardInstances = (1..lines.size).associateWith { 1 }.toMutableMap()

    fun updateInstances(times: Int, range: IntRange) {
        range.forEach { cardInstances[it] = cardInstances[it]!! + times }
    }

    lines.forEachIndexed { index, line ->
        val cardNumber = index + 1
        val matches = getMatches(line)
        val range = cardNumber + 1..(cardNumber + matches).coerceAtMost(lines.size)
        updateInstances(cardInstances[cardNumber] ?: 1, range)
    }

    return cardInstances.values.sum()
}

private fun getMatches(line: String): Int {
    val cards = line.substringAfter(":").split("|")
    val winningCards = cards[0].trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
    val inventoryCards = cards[1].trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
    return winningCards.intersect(inventoryCards).size
}