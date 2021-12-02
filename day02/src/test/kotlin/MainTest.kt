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
        assertEquals(Instruction.Forward(1),Instruction.create("forward 1"))
    }
    @Test
    fun `moving up 1`() {
        assertEquals(Instruction.Up(1),Instruction.create("up 1"))
    }
    @Test
    fun `moving down 1`() {
        assertEquals(Instruction.Down(1),Instruction.create("down 1"))
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
        val instruction = Instruction.create("forward 5")
        assertEquals(Status(5,0,0),executeInstrution(Status(),instruction))
    }
    @Test
    fun `part two move down`() {
        val instruction = Instruction.create("down 5")
        val status = Status(5,0,0)
        assertEquals(Status(5,0,5),executeInstrution(status,instruction))
    }
    @Test
    fun `part forward when aim is 5`() {
        val instruction = Instruction.create("forward 8")
        val status = Status(5,0,5)
        assertEquals(Status(13,40,5),executeInstrution(status,instruction))
    }

    @Test
    fun `part two move up`() {
        val instruction = Instruction.create("up 3")
        val status = Status(13,40,5)
        assertEquals(Status(13,40,2),executeInstrution(status,instruction))
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

