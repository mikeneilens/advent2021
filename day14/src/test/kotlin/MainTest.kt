import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val sampleData = """
    NNCB

    CH -> B
    HH -> N
    CB -> H
    NH -> C
    HB -> C
    HC -> B
    HN -> C
    NN -> C
    BH -> H
    NC -> B
    NB -> B
    BN -> B
    BB -> N
    BC -> B
    CC -> N
    CN -> C
""".trimIndent().split("\n")
class MainTest {
    @Test
    fun `parse sample data`() {
        val (polymerTemplate, pairs) = sampleData.parse()
        assertEquals("NNCB",polymerTemplate)
        assertEquals("B",pairs["CH"])
    }
    @Test
    fun `apply polymer once on sample data`() {
        val (polymerTemplate, pairs) = sampleData.parse()
        assertEquals(listOf("NC", "CN", "NB", "BC", "CH", "HB"), applyPolymer(polymerTemplate.windowed(2,1), pairs))
    }
    @Test
    fun `part one using sample data`() {
        assertEquals(1588, partOne(sampleData))
    }
    @Test
    fun `part one using puzzle input`() {
        assertEquals(2975, partOne(puzzleInput))
    }
    @Test
    fun `part two using sample data input over 10 cycles`() {
        assertEquals( 1588, partTwo(sampleData,10))
    }
    @Test
    fun `part two using sample data input over 40 cycles`() {
        assertEquals( 2188189693529, partTwo(sampleData,40))
    }
    @Test
    fun `part two using puzzle input over 40 cycles`() {
        assertEquals( 3015383850689, partTwo(puzzleInput,40))
    }

}