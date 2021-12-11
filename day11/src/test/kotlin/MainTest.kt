import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    private val sampleData = """
        11111
        19991
        19191
        19991
        11111
    """.trimIndent().split("\n")
    private val bigSampleData = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent().split("\n")
    @Test
    fun `parsing sample data`() {
        val map = sampleData.parse()
        assertEquals(listOf(1, 1, 1, 1, 1), map.filter{it.key.y == 0}.values.toList())
        assertEquals(listOf(1, 9, 9, 9, 1), map.filter{it.key.y == 1}.values.toList())
        assertEquals(listOf(1, 9, 1, 9, 1), map.filter{it.key.y == 2}.values.toList())
        assertEquals(listOf(1, 9, 9, 9, 1), map.filter{it.key.y == 3}.values.toList())
        assertEquals(listOf(1, 1, 1, 1, 1), map.filter{it.key.y == 4}.values.toList())
    }

    @Test
    fun `updating octopus grid by one`() {
        val map = sampleData.parse()
        map.increaseEnergyByOne()
        assertEquals(listOf(2, 2, 2, 2, 2), map.filter{it.key.y == 0}.values.toList())
        assertEquals(listOf(2, 10, 10, 10, 2), map.filter{it.key.y == 1}.values.toList())
        assertEquals(listOf(2, 10, 2, 10, 2), map.filter{it.key.y == 2}.values.toList())
        assertEquals(listOf(2, 10, 10, 10, 2), map.filter{it.key.y == 3}.values.toList())
        assertEquals(listOf(2, 2, 2, 2, 2), map.filter{it.key.y == 4}.values.toList())
    }
    @Test
    fun `updating positions surrounding flashers`() {
        val map = sampleData.parse()
        map.increaseEnergyByOne()
        map.upDateOctopusSurroundingFlashers()
        assertEquals(listOf(3, 4, 5, 4, 3), map.filter{it.key.y == 0}.values.toList())
        assertEquals(listOf(4, 13, 15, 13, 4), map.filter{it.key.y == 1}.values.toList())
        assertEquals(listOf(5, 15, 10, 15, 5), map.filter{it.key.y == 2}.values.toList())
        assertEquals(listOf(4, 13, 15, 13, 4), map.filter{it.key.y == 3}.values.toList())
        assertEquals(listOf(3, 4, 5, 4, 3), map.filter{it.key.y == 4}.values.toList())
    }
    @Test
    fun `process a step using sample data`() {
        val map = sampleData.parse()
        map.processStep()
        assertEquals(listOf(3,4,5,4,3),map.filter{it.key.y == 0}.values.toList())
        assertEquals(listOf(4,0,0,0,4),map.filter{it.key.y == 1}.values.toList())
        assertEquals(listOf(5,0,0,0,5),map.filter{it.key.y == 2}.values.toList())
        assertEquals(listOf(4,0,0,0,4),map.filter{it.key.y == 3}.values.toList())
        assertEquals(listOf(3,4,5,4,3),map.filter{it.key.y == 4}.values.toList())
    }

    @Test
    fun `part one using big sample data`() {
        assertEquals(204, partOne(bigSampleData, 10))
        assertEquals(1656, partOne(bigSampleData, 100))
    }
    @Test
    fun `part one using puzzle input`() {
        assertEquals(1683, partOne(puzzleInput, 100))
    }

    @Test
    fun `part two using big sample data`() {
        assertEquals(195, partTwo(bigSampleData))
    }

    @Test
    fun `part two using puzzle input`() {
        assertEquals(788, partTwo(puzzleInput))
    }
}