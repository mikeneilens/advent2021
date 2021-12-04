import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7
    """.trimIndent()
    @Test
    fun `obtaining list of numbers of numbers for sample data`() {
        val expectedResult = listOf(
            7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
        )
        assertEquals(expectedResult,getNumbers(sampleData))
    }
    @Test
    fun `obtaining boards for sample data`() {
        val expectedResultForBoard2Row4 = listOf(
            BoardItem(2),BoardItem(0),BoardItem(12),BoardItem(3),BoardItem(7)
        )
        assertEquals(expectedResultForBoard2Row4,getBoards(sampleData)[2].numbers[4])
    }
    @Test
    fun `move rows to columns`() {
        val grid = listOf(listOf(1,2),listOf(3,4),listOf(5,6))
        assertEquals(listOf(listOf(1,3,5),listOf(2,4,6)), grid.rowToColumn())
    }
    @Test
    fun `no wining row or column`() {
        val board = getBoards(sampleData)[0]
        assertEquals(null,winningNumbers(board))
    }
    @Test
    fun `wining row`() {
        val board = getBoards(sampleData)[0]
        board.numbers[0][0].called = true
        board.numbers[0][1].called = true
        board.numbers[0][2].called = true
        board.numbers[0][3].called = true
        board.numbers[0][4].called = true

        assertEquals(board,winningNumbers(board))
    }
    @Test
    fun `wining column`() {
        val board = getBoards(sampleData)[0]
        board.numbers[0][0].called = true
        board.numbers[1][0].called = true
        board.numbers[2][0].called = true
        board.numbers[3][0].called = true
        board.numbers[4][0].called = true

        assertEquals(board,winningNumbers(board))
    }
    @Test
    fun `winning numbers`(){
        val numbers = getNumbers(sampleData)
        val boards = getBoards(sampleData)
        val expectedResult = Pair(24,boards[2]
        )
        assertEquals(expectedResult, callNumberUntilWinner(numbers, boards))
    }
    @Test
    fun `part one with sample data`() {
        assertEquals(4512, partOne(sampleData))
    }
    @Test
    fun `part one with puzzleInput`() {
        assertEquals(11774, partOne(puzzleInput))
    }
    @Test
    fun `part two with sample data`() {
        assertEquals(1924, partTwo(sampleData))
    }
    @Test
    fun `part two with puzzleInput`() {
        assertEquals(4495, partTwo(puzzleInput))
    }

}

