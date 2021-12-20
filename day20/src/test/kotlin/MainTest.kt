import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleAlgorithm = """
        ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##
        #..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###
        .######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.
        .#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....
        .#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..
        ...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....
        ..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
    """.trimIndent().split("\n").joinToString("")

    val sampleImage = """
            #..#.
            #....
            ##..#
            ..#..
            ..###
        """.trimIndent().split("\n")
    @Test
    fun `rows for a position`() {
        val position = Position(3,5)
        val row0 = listOf(Position(2,4),Position(3,4),Position(4,4))
        val row1 = listOf(Position(2,5),Position(3,5),Position(4,5))
        val row2 = listOf(Position(2,6),Position(3,6),Position(4,6))
        assertEquals(listOf(row0,row1,row2), position.rows())
    }
    @Test
    fun `printing image`() {
        println(createImage(sampleImage).text())
    }
    @Test
    fun `getting binary number for a position`() {
        val result = createImage(sampleImage).getBinary(Position(2,2))
        assertEquals("000100010", result)
    }
    @Test
    fun `applying algorithm to sample data`() {
        val result = createImage((sampleImage)).applyAlgorithmToImage(sampleAlgorithm,4)
        assertEquals("...##.##...", result.text().split("\n")[2])
        assertEquals("..#..#.#...", result.text().split("\n")[3])
        assertEquals("..##.#..#..", result.text().split("\n")[4])
        assertEquals("..####..#..", result.text().split("\n")[5])
        assertEquals("...#..##...", result.text().split("\n")[6])
        assertEquals("....##..#..", result.text().split("\n")[7])
        assertEquals(".....#.#...", result.text().split("\n")[8])
    }
    @Test
    fun `applying algorithm to sample data twice`() {
        assertEquals(35, partOne(sampleAlgorithm, sampleImage))
    }
    @Test
    fun `part one with puzzle input`() {
        assertEquals(5326, partOne(puzzleAlgorithm, puzzleImage))
    }
    @Test
    fun `part two with sample data`() {
        assertEquals(3351, partTwo(sampleAlgorithm, sampleImage))
    }
    @Test
    fun `part two with puzzle input`() {
        assertEquals(3351, partTwo(puzzleAlgorithm, puzzleImage))
    }

}
