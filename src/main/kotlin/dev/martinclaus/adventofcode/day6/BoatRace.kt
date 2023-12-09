package dev.martinclaus.adventofcode.day6

import kotlin.test.assertEquals

/**
 * @see <a href="https://adventofcode.com/2023/day/6">Advent of Code 2023 Day 6</a>
 */
fun main() {
    val testInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    val input = """
        Time:        35     69     68     87
        Distance:   213   1168   1086   1248
    """.trimIndent()

    assertEquals(288, partI(testInput))
    val answer1 = partI(input)
    assertEquals(170000, answer1)

    println("Day 6: Wait For It")
    println("Part I: What do you get if you multiply these numbers together? $answer1")

    assertEquals(71503, partII(testInput))
    val answer2 = partII(input)
    assertEquals(20537782, answer2)

    println("Part II: How many ways can you beat the record in this one much longer race? $answer2")
}

private val pattern = "\\d+".toRegex().toPattern()

private fun partI(input: String): Int {
    return calculateRacePossibilities(input, ::getRacesData).reduce(Int::times)
}

fun partII(input: String): Int {
    return calculateRacePossibilities(input, ::getRacesDataAsOne).first()
}

private fun calculateRacePossibilities(input: String, racesData: (String) -> List<Pair<Long, Long>>): List<Int> {
    val races = racesData(input)
    return races.map { (time, distance) ->
        (1..time).filter { (time - it) * it > distance }.size
    }
}

private fun getRacesData(input: String): List<Pair<Long, Long>> {
    val (time, distance) = input.split("\n", limit = 2)
        .map { pattern.matcher(it).results().map { number -> number.group().toLong() }.toList() }

    return time.zip(distance)
}

private fun getRacesDataAsOne(input: String): List<Pair<Long, Long>> {
    val (time, distance) = input.split("\n", limit = 2)
        .map { pattern.matcher(it).results().map { number -> number.group() }.reduce(String::plus) }

    return listOf(time.get().toLong() to distance.get().toLong())
}
