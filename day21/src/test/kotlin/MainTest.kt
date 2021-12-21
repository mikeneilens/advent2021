import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `playing a game using starting positions of 4 and 8`() {
        val game = Game(Player(4),Player(8)).apply { playGame() }
        assertEquals(739785, game.result())
    }

    @Test
    fun `playing a game using puzzle input`() {
        val game = Game(Player(6),Player(9)).apply { playGame() }
        assertEquals(925605, game.result())
    }
    @Test
    fun `test counting wins for part two using starting position of 4 and 8`() {
        assertEquals(ScoreCard(444356092776315, 341960390180808), countWins(Player(4), Player(8)))
    }
    @Test
    fun `part two using puzzle input`() {
        assertEquals(486638407378784L, countWins(Player(6),Player(9)).highestNumberOfWins())
    }
}

