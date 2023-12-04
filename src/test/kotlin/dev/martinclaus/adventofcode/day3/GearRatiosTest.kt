package dev.martinclaus.adventofcode.day3

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GearRatiosTest {
    @Test
    fun `check that sum of part numbers is correct`() {
        val input = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
        """.trimIndent()

        val result = GearRatios().sumAllPartNumbers(input)

        assertEquals(4361, result)
    }

    @Test
    fun `check that sum of all gear ratios is correct`() {
        val input = """
            .......5......
            ..7*..*.......
            ...*13*.......
            .......15.....
        """.trimIndent()

        val result = GearRatios().sumGears(input)

        assertEquals(442, result)
    }
}