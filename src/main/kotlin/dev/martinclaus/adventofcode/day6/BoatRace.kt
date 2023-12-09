package dev.martinclaus.adventofcode.day6

import dev.martinclaus.adventofcode.printSolution
import dev.martinclaus.adventofcode.readLines

private typealias Lines = List<String>

/**
 * @see <a href="https://adventofcode.com/2023/day/6">Advent of Code 2023 Day 6</a>
 */
fun main() {
    val input = "boat-race.txt".readLines()
    printSolution(
        6,
        "Wait For It",
        "What do you get if you multiply these numbers together? ${partI(input)}",
        "How many ways can you beat the record in this one much longer race? ${partII(input)}",
    )
}

private val pattern = "\\d+".toRegex().toPattern()

fun partI(lines: Lines): Int = calculateRacePossibilities(lines, ::getRacesData).reduce(Int::times)

fun partII(lines: Lines): Int = calculateRacePossibilities(lines, ::getRacesDataAsOne).first()

private fun calculateRacePossibilities(lines: Lines, racesData: (Lines) -> List<Pair<Long, Long>>): List<Int> {
    val races = racesData(lines)
    return races.map { (time, distance) ->
        (1..time).filter { (time - it) * it > distance }.size
    }
}

private fun getRacesData(lines: Lines): List<Pair<Long, Long>> {
    val (time, distance) = lines.map {
        pattern.matcher(it).results().map { number -> number.group().toLong() }.toList()
    }

    return time.zip(distance)
}

private fun getRacesDataAsOne(lines: Lines): List<Pair<Long, Long>> {
    val (time, distance) = lines.map {
        pattern.matcher(it).results().map { number -> number.group() }.reduce(String::plus)
    }

    return listOf(time.get().toLong() to distance.get().toLong())
}
