package dev.martinclaus.adventofcode.day4

import kotlin.math.pow

fun main() {
    val scratchCards = ScratchCards()
    val input = scratchCards::class.java.classLoader.getResource("scratchcards.txt")?.readText()
    val answer1 = scratchCards.partI(input.orEmpty())
    val answer2 = scratchCards.partII(input.orEmpty())

    check(answer1 == 23750)
    check(answer2 == 13261850)

    println("Day 4: Scratchcards")
    println("Part I: How many points are they worth in total? $answer1")
    println("Part II: How many total scratchcards do you end up with? $answer2")
}

/**
 * @see <a href="https://adventofcode.com/2023/day/4">Advent of Code 2023 Day 4</a>
 */
class ScratchCards {
    fun partI(scratchCards: String): Int {
        val lines = scratchCards.lines()
        return lines.sumOf { line ->
            val matches = getMatches(line)
            2.0.pow(matches - 1.0).toInt()
        }
    }

    fun partII(scratchCards: String): Int {
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
}