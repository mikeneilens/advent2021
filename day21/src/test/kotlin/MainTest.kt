import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `rolling dice`() {
        assertEquals(listOf(1,2,3),dice(1))
        assertEquals(listOf(4,5,6),dice(2))
        assertEquals(listOf(100,1,2),dice(34))
    }
    @Test
    fun `caculating new position`() {
        assertEquals(9, newPosition(7,2))
        assertEquals(10, newPosition(7,3))
        assertEquals(2, newPosition(7,5))
    }
    @Test
    fun `playing a game using starting positions of 4 and 8`() {
        assertEquals(739785, playGame(4,8))
    }

    @Test
    fun `playing a game using puzzle input`() {
        assertEquals(925605, playGame(6,9))
    }

}

