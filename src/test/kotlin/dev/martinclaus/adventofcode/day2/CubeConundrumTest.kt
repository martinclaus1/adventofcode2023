package dev.martinclaus.adventofcode.day2

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import java.util.stream.Stream.of
import kotlin.test.assertEquals

class CubeConundrumTest {
    private val validator = CubeConundrum()

    companion object {
        val INPUT = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent()

        val EXAMPLE_CONUNDRUM = listOf(
            Conundrum(1, listOf(ConundrumSet(4, 0, 3), ConundrumSet(1, 2, 6), ConundrumSet(0, 2, 0))),
            Conundrum(2, listOf(ConundrumSet(0, 2, 1), ConundrumSet(1, 3, 4), ConundrumSet(0, 1, 1))),
            Conundrum(3, listOf(ConundrumSet(20, 8, 6), ConundrumSet(4, 13, 5), ConundrumSet(1, 5, 0))),
            Conundrum(4, listOf(ConundrumSet(3, 1, 6), ConundrumSet(6, 3, 0), ConundrumSet(14, 3, 15))),
            Conundrum(5, listOf(ConundrumSet(6, 3, 1), ConundrumSet(1, 2, 2)))
        )

        val BAG = ConundrumSet(12, 13, 14)
    }

    @Test
    fun `check that cube conundrum will be parsed`() {
        val result = validator.parse(INPUT)

        assertEquals(EXAMPLE_CONUNDRUM, result)
    }

    @ParameterizedTest
    @ArgumentsSource(CubeConundrumValidationArgumentsProvider::class)
    fun `check validation`(cubeConundrum: List<ConundrumSet>, expected: Boolean) {
        val result = validator.validate(cubeConundrum, BAG)
        assertEquals(expected, result)
    }

    @Test
    fun `check that cube conundrum will be solved`() {
        val result = validator.partI(INPUT, BAG)

        assertEquals(8, result)
    }

    @Test
    fun `check that power will be calculated`() {
        val result = validator.partII(INPUT)

        assertEquals(2286, result)
    }
}

class CubeConundrumValidationArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = of(
        Arguments.of(CubeConundrumTest.EXAMPLE_CONUNDRUM[0].subsets, true),
        Arguments.of(CubeConundrumTest.EXAMPLE_CONUNDRUM[1].subsets, true),
        Arguments.of(CubeConundrumTest.EXAMPLE_CONUNDRUM[2].subsets, false),
        Arguments.of(CubeConundrumTest.EXAMPLE_CONUNDRUM[3].subsets, false),
        Arguments.of(CubeConundrumTest.EXAMPLE_CONUNDRUM[4].subsets, true),
    )
}