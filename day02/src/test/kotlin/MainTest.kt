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
        assertEquals(Order(Instruction.Forward,1),"forward 1".toOrder())
    }
    @Test
    fun `moving up 1`() {
        assertEquals(Order(Instruction.Up,1),"up 1".toOrder())
    }
    @Test
    fun `moving down 1`() {
        assertEquals(Order(Instruction.Down,1),"down 1".toOrder())
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
        val order = "forward 5".toOrder()
        assertEquals(Status(Vector(5,0),0),executeOrder2(Status(),order))
    }
    @Test
    fun `part two move down`() {
        val order = "down 5".toOrder()
        val status = Status(Vector(5,0),0)
        assertEquals(Status(Vector(5,0),5),executeOrder2(status,order))
    }
    @Test
    fun `part forward when aim is 5`() {
        val order = "forward 8".toOrder()
        val status = Status(Vector(5,0),5)
        assertEquals(Status(Vector(13,40),5),executeOrder2(status,order))
    }

    @Test
    fun `part two move up`() {
        val order = "up 3".toOrder()
        val status = Status(Vector(13,40),5)
        assertEquals(Status(Vector(13,40),2),executeOrder2(status,order))
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

