package dev.martinclaus.adventofcode.day5

import dev.martinclaus.adventofcode.AdventOfCodeTask

class Greenhouse : AdventOfCodeTask {
    override fun solve() {
        val input = this::class.java.classLoader.getResource("almanac.txt")?.readText().orEmpty()
        println("Day 5: Greenhouse")
        println(
            "\tPart I: What is the lowest location number that corresponds to any of the initial seed numbers? ${
                parseAlmanacWithSeeds(
                    input
                )
            }"
        )
        println(
            "\tPart II: What is the lowest location number that corresponds to any of the initial seed numbers (listed in ranges)? ${
                parseAlmanacWithSeedRanges(
                    input
                )
            }"
        )
    }

    fun parseAlmanacWithSeeds(input: String): Long {
        val seeds = input.substringAfter("seeds:").substringBefore("\n").trim().split(" ").map { it.toLong() }
        val seedsToSoil = input.parseMap("seed-to-soil map:")
        val soilToFertilizer = input.parseMap("soil-to-fertilizer map:")
        val fertilizerToWater = input.parseMap("fertilizer-to-water map:")
        val waterToLight = input.parseMap("water-to-light map:")
        val lightToTemperature = input.parseMap("light-to-temperature map:")
        val temperatureToHumidity = input.parseMap("temperature-to-humidity map:")
        val humidityToLocation = input.parseMap("humidity-to-location map:")
        val maps = listOf(
            seedsToSoil,
            soilToFertilizer,
            fertilizerToWater,
            waterToLight,
            lightToTemperature,
            temperatureToHumidity,
            humidityToLocation
        )

        val min = seeds.minOfOrNull {
            var value = it
            for (map in maps) {
                value = getDestination(map, value)
            }
            value
        }

        return min ?: 0
    }

    fun parseAlmanacWithSeedRanges(input: String): Long {
        val seeds =
            input.substringAfter("seeds:").substringBefore("\n").trim().split(" ").map { it.toLong() }.chunked(2)
                .map { (start, range) ->
                    (start..<start + range)
                }
        val seedsToSoil = input.parseMap("seed-to-soil map:")
        val soilToFertilizer = input.parseMap("soil-to-fertilizer map:")
        val fertilizerToWater = input.parseMap("fertilizer-to-water map:")
        val waterToLight = input.parseMap("water-to-light map:")
        val lightToTemperature = input.parseMap("light-to-temperature map:")
        val temperatureToHumidity = input.parseMap("temperature-to-humidity map:")
        val humidityToLocation = input.parseMap("humidity-to-location map:")
        val maps = listOf(
            seedsToSoil,
            soilToFertilizer,
            fertilizerToWater,
            waterToLight,
            lightToTemperature,
            temperatureToHumidity,
            humidityToLocation
        )

        val min = seeds.minOfOrNull { range ->
            println(range)
            range.minOf {
                var value = it
                for (map in maps) {
                    value = getDestination(map, value)
                }
                value
            }
        }

        return min ?: 0
    }

    private fun getDestination(map: List<List<Long>>, value: Long): Long {
        for ((destination, source, range) in map) {
            if (value in source..<source + range) {
                return destination - source + value
            }
        }
        return value
    }

    private fun String.parseMap(delimiter: String) =
        substringAfter(delimiter).substringBefore("\n\n")
            .trim()
            .split("\n")
            .map { row -> row.trim().split(" ").map { entry -> entry.toLong() } }
}