import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val sampleData = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
""".trimIndent().split("\n")

class MainTest {
    @Test
    fun `moving forward 1`() {
        val status = Submarine(1,2,3)
        assertEquals(Submarine(2,2,3),createInstruction("forward 1").execute(status))
    }
    @Test
    fun `moving up 1`() {
        val status = Submarine(1,2,3)
        assertEquals(Submarine(1,1,3),createInstruction("up 1").execute(status))
    }
    @Test
    fun `moving down 1`() {
        val status = Submarine(1,2,3)
        assertEquals(Submarine(1,3,3),createInstruction("down 1").execute(status))
    }
    @Test
    fun `part one using sample data`() {
        assertEquals(150, partOne(sampleData))
    }
    @Test
    fun `part one using puzzle input`() {
        assertEquals(1383564, partOne(puzzleInput))
    }

    @Test
    fun `part two move foward when aim is zero`() {
        val instruction = createInstruction("forward 5")
        assertEquals(Submarine(5,0,0),instruction.executeP2(Submarine()))
    }
    @Test
    fun `part two move down`() {
        val instruction = createInstruction("down 5")
        val status = Submarine(5,0,0)
        assertEquals(Submarine(5,0,5),instruction.executeP2(status))
    }
    @Test
    fun `part forward when aim is 5`() {
        val instruction = createInstruction("forward 8")
        val status = Submarine(5,0,5)
        assertEquals(Submarine(13,40,5),instruction.executeP2(status))
    }
    @Test
    fun `part two move up`() {
        val instruction = createInstruction("up 3")
        val status = Submarine(13,40,5)
        assertEquals(Submarine(13,40,2),instruction.executeP2(status))
    }
    @Test
    fun `part two using sample data`() {
        assertEquals(900, partTwo(sampleData))
    }
    @Test
    fun `part two using puzzle input`() {
        assertEquals(1488311643, partTwo(puzzleInput))
    }
}

