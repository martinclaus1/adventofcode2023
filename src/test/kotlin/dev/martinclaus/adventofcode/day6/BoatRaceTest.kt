package dev.martinclaus.adventofcode.day6

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BoatRaceTest {
    private val input = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()
    private val boatRace = BoatRace()

    @Test
    fun `check that part I calculates the result correctly`() {
        val result = boatRace.partI(input)

        assertEquals(288, result)
    }

    @Test
    fun `check that part II calculates the result correctly`() {
        val result = boatRace.partII(input)

        assertEquals(71503, result)
    }
}