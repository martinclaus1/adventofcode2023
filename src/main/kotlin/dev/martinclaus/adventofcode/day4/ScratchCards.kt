package dev.martinclaus.adventofcode.day4

import dev.martinclaus.adventofcode.readText
import kotlin.math.pow
import kotlin.test.assertEquals


/**
 * @see <a href="https://adventofcode.com/2023/day/4">Advent of Code 2023 Day 4</a>
 */
fun main() {
    val testInput = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent()
    val input = readText("scratchcards.txt")

    assertEquals(13, partI(testInput))
    val answer1 = partI(input)
    assertEquals(23750, answer1)

    println("Day 4: Scratchcards")
    println("Part I: How many points are they worth in total? $answer1")

    assertEquals(30, partII(testInput))
    val answer2 = partII(input)
    assertEquals(13261850, answer2)

    println("Part II: How many total scratchcards do you end up with? $answer2")
}

private fun partI(scratchCards: String): Int {
    val lines = scratchCards.lines()
    return lines.sumOf { line ->
        val matches = getMatches(line)
        2.0.pow(matches - 1.0).toInt()
    }
}

private fun partII(scratchCards: String): Int {
    val lines = scratchCards.lines()
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