package dev.martinclaus.adventofcode.day8

import dev.martinclaus.adventofcode.readLines
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class HauntedWastelandTest {
    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun `check part I`(input: List<String>, expected: Int) {
        val actual = partI(input)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun `check part II`(input: List<String>, expected: Long) {
        val actual = partII(input)

        assertEquals(expected, actual)
    }
}

class PartIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
        Arguments.of(
            """
            RL
            
            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
            """.trimIndent().lines(),
            2
        ),
        Arguments.of(
            """
            LLR

            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
            """.trimIndent().lines(),
            6
        ),
        Arguments.of(
            "haunted-wasteland.txt".readLines(),
            12599
        )
    )
}

class PartIIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
        Arguments.of(
            """
            LR
            
            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
            """.trimIndent().lines(),
            6
        ),
        Arguments.of("haunted-wasteland.txt".readLines(), 8245452805243)
    )
}