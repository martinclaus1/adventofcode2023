package dev.martinclaus.adventofcode.day5

import dev.martinclaus.adventofcode.printSolution
import dev.martinclaus.adventofcode.readLines

/**
 * @see <a href="https://adventofcode.com/2023/day/5">Advent of Code 2023 Day 5</a>
 */
fun main() {
    val input = "greenhouse.txt".readLines()
    printSolution(
        5,
        "If You Give A Seed A Fertilizer",
        "What is the lowest location number that corresponds to any of the initial seed numbers? ${partI(input)}",
        "What is the lowest location number that corresponds to any of the initial seed numbers (listed in ranges)? ${partII(input)}",
    )
}

fun partI(lines: List<String>): Long {
    val (seeds, mappings) = parse(lines, ::parseSeeds)
    return seeds.minOf { it.minOf { seed -> mappings.fold(seed) { acc, map -> map.lookup(acc) } } }
}

fun partII(lines: List<String>): Long {
    val (seeds, mappings) = parse(lines, ::parseSeedRanges)
    val reversedMappings = mappings.reversed()

    return generateSequence(0L) { it + 1 }.filter { location ->
        val seed = reversedMappings.fold(location) { acc, map -> map.reverseLookup(acc) }
        seeds.any { seed in it }
    }.first()
}

private fun parse(lines: List<String>, seedParser: (String) -> List<LongRange>): Pair<List<LongRange>, List<Mapping>> {
    val seeds = lines.first()
        .substringAfter(":")
        .trim()
        .let(seedParser)
    val mappings = lines.drop(2).joinToString("\n").split("\n\n").map(Mapping::invoke)

    return seeds to mappings
}

private fun parseSeeds(input: String): List<LongRange> =
    input.split(' ').map { it.toLong()..it.toLong() }

private fun parseSeedRanges(input: String): List<LongRange> =
    input
        .split(' ')
        .map(String::toLong)
        .chunked(2)
        .map { (lower, length) -> lower..(lower + length) }

private data class Item(val sourceStart: Long, val destinationStart: Long, val length: Long) {
    private val sourceRange = sourceStart..(sourceStart + length)
    private val destinationRange = destinationStart..(destinationStart + length)

    fun lookup(source: Long) =
        if (source in sourceRange) {
            destinationStart + (source - sourceStart)
        } else {
            null
        }

    fun reverseLookup(destination: Long) =
        if (destination in destinationRange) {
            sourceStart + (destination - destinationStart)
        } else {
            null
        }

    companion object {
        operator fun invoke(input: String): Item {
            val (destinationStart, sourceStart, length) = input.trim().split(" ").map(String::toLong)
            return Item(sourceStart, destinationStart, length)
        }
    }
}

private data class Mapping(val entries: List<Item>) {
    fun lookup(source: Long): Long =
        entries.firstNotNullOfOrNull { it.lookup(source) } ?: source

    fun reverseLookup(destination: Long): Long =
        entries.firstNotNullOfOrNull { it.reverseLookup(destination) } ?: destination

    companion object {
        operator fun invoke(input: String): Mapping =
            Mapping(input.trim().lines().drop(1).map(Item::invoke))
    }
}