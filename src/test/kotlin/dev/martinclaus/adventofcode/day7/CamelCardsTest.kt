package dev.martinclaus.adventofcode.day7

import dev.martinclaus.adventofcode.readLines
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class CamelCardsTest {
    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun `check part I`(lines: List<String>, expected: Int) {
        val actual = partI(lines)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun `check part II`(lines: List<String>, expected: Int) {
        val actual = partII(lines)

        assertEquals(expected, actual)
    }
}

class PartIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
        Arguments.of(
            """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
            """.trimIndent().lines(),
            6440
        ),
        Arguments.of(
            "camel-cards.txt".readLines(),
            251545216
        )
    )
}

class PartIIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
        Arguments.of(
            """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
            """.trimIndent().lines(),
            5905
        ),
        Arguments.of(
            "camel-cards.txt".readLines(),
            250384185
        )
    )
}