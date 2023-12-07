import dev.martinclaus.adventofcode.readText
import kotlin.test.assertEquals

/**
 * @see <a href="https://adventofcode.com/2023/day/7">Advent of Code 2023 Day 7</a>
 */
fun main() {
    val testInput = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    val testInput2 = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()
    val input = readText("camel-cards.txt")

    assertEquals(6440, partI(testInput))
    val answer1 = partI(input)
    assertEquals(251545216, answer1)

    println("Day 7: Camel Cards")
    println("Part I: What are the total winnings? $answer1")

    assertEquals(5905, partII(testInput2))
    val answer2 = partII(input)
    assertEquals(250384185, answer2)

    println("Part II: What are the new total winnings? $answer2")
}
private fun partI(input: String): Int {
    val hands = getHands(input, cards, ::getType).sorted().reversed()
    return hands.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()
}

private fun partII(input: String): Int {
    val hands = getHands(input, cards2, ::findBestType).sorted().reversed()
    return hands.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()
}

private fun getHands(input: String, cardRanking: Set<Char>, calculateCardType: (String) -> CardType): List<Hand> =
    input.split("\n").map {
        val (hand, bid) = it.split(" ", limit = 2)
        Hand(
            cards = hand.trim(),
            bid = bid.trim().toInt(),
            type = calculateCardType(hand),
            cardRanking = cardRanking
        )
    }

private fun findBestType(hand: String): CardType {
    if (!hand.contains('J')) {
        return getType(hand)
    }

    val value = cards2 - setOf('J')
    return value.associateWith { getType(hand.replace('J', it)) }.entries.minByOrNull { it.value }!!.value
}

private data class Hand(
    val cards: String,
    val bid: Int,
    val type: CardType,
    val cardRanking: Set<Char>
) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        val rank = type.compareTo(other.type)

        return if (rank == 0) {
            compareCards(cards, other.cards, 0)
        } else {
            rank
        }
    }

    private fun compareCards(firstCard: String, secondCard: String, index: Int): Int {
        val a: Char = firstCard[index]
        val b: Char = secondCard[index]

        val rank: Int = cardRanking.indexOf(a).compareTo(cardRanking.indexOf(b))
        if (rank != 0 || index == firstCard.length - 1) {
            return rank
        }

        return compareCards(firstCard, secondCard, index + 1)
    }
}

private fun getType(input: String): CardType {
    val groups = input.groupBy { it }
    return when (groups.size) {
        1 -> CardType.FIVE_OF_A_KIND
        2 -> {
            if (groups.values.any { it.size == 4 }) {
                CardType.FOUR_OF_A_KIND
            } else {
                CardType.FULL_HOUSE
            }
        }

        3 -> {
            if (groups.values.any { it.size == 3 }) {
                CardType.THREE_OF_A_KIND
            } else {
                CardType.TWO_PAIR
            }
        }

        4 -> CardType.ONE_PAIR
        else -> CardType.HIGH_CARD
    }
}

enum class CardType : Comparable<CardType> {
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD;

}

private val cards = setOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
private val cards2 = setOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')