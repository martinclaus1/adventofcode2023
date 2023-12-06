package dev.martinclaus.adventofcode.day5

fun main() {
    val greenhouse = Greenhouse()
    val input = greenhouse::class.java.classLoader.getResource("almanac.txt")?.readText().orEmpty()
    val answer1 = greenhouse.partI(input)
    val answer2 = greenhouse.partII(input)

    check(answer1 == 226172555L)
    check(answer2 == 47909639L)

    println("Day 5: If You Give A Seed A Fertilizer")
    println("Part I: What is the lowest location number that corresponds to any of the initial seed numbers? $answer1")
    println("Part II: What is the lowest location number that corresponds to any of the initial seed numbers (listed in ranges)? $answer2")
}

/**
 * @see <a href="https://adventofcode.com/2023/day/5">Advent of Code 2023 Day 5</a>
 */
class Greenhouse {
    fun partI(input: String): Long {
        val (seeds, mappings) = parse(input, ::parseSeeds)
        return seeds.minOf { it.minOf { seed -> mappings.fold(seed) { acc, map -> map.lookup(acc) } } }
    }

    fun partII(input: String): Long {
        val (seeds, mappings) = parse(input, ::parseSeedRanges)
        val reversedMappings = mappings.reversed()

        return generateSequence(0L) { it + 1 }.filter { location ->
            val seed = reversedMappings.fold(location) { acc, map -> map.reverseLookup(acc) }
            seeds.any { seed in it }
        }.first()
    }

    private fun parse(input: String, seedParser: (String) -> List<LongRange>): Pair<List<LongRange>, List<Mapping>> {
        val (seedsString, rawMappings) = input.trim().split('\n', limit = 2)
        val seeds = seedsString
            .substringAfter(":")
            .trim()
            .let(seedParser)
        val mappings = rawMappings.trim().split("\n\n").map(Mapping::invoke)

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

}

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