import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

val sampleData = """
    6,10
    0,14
    9,10
    0,3
    10,4
    4,11
    6,0
    6,12
    4,1
    0,13
    10,12
    3,4
    3,0
    8,4
    1,10
    2,14
    8,10
    9,0
    fold along y=7
    fold along x=5
""".trimIndent().split("\n")

class MainTest {
    @Test
    fun `parse sample data`() {
        val (paper, folds) = sampleData.parse()
        assertEquals(2, folds.size)
        assertTrue(Position(6,10) in paper)
        assertTrue(Position(10,12) in paper)
    }
    @Test
    fun `folding sample data along y=7`() {
        val (paper, folds) = sampleData.parse()
        val foldedPaper = folds[0](paper)
        assertTrue(Position(0,0) in foldedPaper)
        assertFalse(Position(1,0) in foldedPaper)
        assertTrue(Position(2,0) in foldedPaper)
        assertTrue(Position(3,0) in foldedPaper)
        assertFalse(Position(4,0) in foldedPaper)
        assertFalse(Position(5,0) in foldedPaper)
        assertTrue(Position(6,0) in foldedPaper)
        assertFalse(Position(7,0) in foldedPaper)
        assertFalse(Position(8,0) in foldedPaper)
        assertTrue(Position(9,0) in foldedPaper)

        assertEquals(17, foldedPaper.size)
    }
    @Test
    fun `folding sample data along y=7 and then x = 5`() {
        val (paper, folds) = sampleData.parse()
        val foldedPaper = folds[1](folds[0](paper))
        assertTrue(Position(0,0) in foldedPaper)
        assertTrue(Position(1,0) in foldedPaper)
        assertTrue(Position(2,0) in foldedPaper)
        assertTrue(Position(3,0) in foldedPaper)
        assertTrue(Position(4,0) in foldedPaper)

        assertTrue(Position(0,1) in foldedPaper)
        assertFalse(Position(1,1) in foldedPaper)
        assertFalse(Position(2,1) in foldedPaper)
        assertFalse(Position(3,1) in foldedPaper)
        assertTrue(Position(4,1) in foldedPaper)

        assertTrue(Position(0,2) in foldedPaper)
        assertFalse(Position(1,2) in foldedPaper)
        assertFalse(Position(2,2) in foldedPaper)
        assertFalse(Position(3,2) in foldedPaper)
        assertTrue(Position(4,2) in foldedPaper)

        assertTrue(Position(0,4) in foldedPaper)
        assertTrue(Position(1,4) in foldedPaper)
        assertTrue(Position(2,4) in foldedPaper)
        assertTrue(Position(3,4) in foldedPaper)
        assertTrue(Position(4,4) in foldedPaper)

        assertEquals(16, foldedPaper.size)
    }

    @Test
    fun `part one using puzzle input`() {
        val newMap = partOne(puzzleInput)
        assertEquals(712, newMap.size)
    }
    @Test
    fun `part two using puzzle input`() {
        val newMap = partTwo(puzzleInput)
        newMap.print()
    }
}