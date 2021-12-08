import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val testData = """
    acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf
""".trimIndent().split("\n")
val sampleData = """
    be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
    edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
    fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
    fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
    aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
    fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
    dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
    bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
    egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
    gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
""".trimIndent().split("\n")

class MainTest {
    @Test
    fun `parsing test data`() {
        val result = parse(testData)
        val expectedSignals =  listOf("acedgfb".toSet(), "cdfbe".toSet(), "gcdfa".toSet(), "fbcad".toSet(),
            "dab".toSet(), "cefabd".toSet(), "cdfgeb".toSet(), "eafb".toSet(), "cagedb".toSet(), "ab".toSet())
        val expectedSegments = listOf("cdfeb".toSet(), "fcadb".toSet(), "cdfeb".toSet(), "cdbaf".toSet(),)
        assertEquals(1,result.size)
        assertEquals(expectedSignals, result[0].signalWires)
        assertEquals(expectedSegments, result[0].segments)
    }
    @Test
    fun `digitsThatContainOneFourSevenEight using test data`() {
        val displays = parse(testData)
        val result = displays.digitsContainingFourSevenEight()
        assertEquals(listOf<String>(), result)
    }
    @Test
    fun `part one using test data`() {
        assertEquals(0, partOne((testData)))
    }
    @Test
    fun `part one using sample data`() {
        assertEquals(26, partOne((sampleData)))
    }
    @Test
    fun `part one using puzzleInput`() {
        assertEquals(294, partOne((puzzleInput)))
    }
    @Test
    fun `puzzleInput findNumbers`() {
        val display = parse(testData)[0]
        assertEquals("ab".toSet(),display.wiresForOne)
        assertEquals("eafb".toSet(),display.wiresForFour)
        assertEquals("dab".toSet(),display.wiresForSeven)
        assertEquals("acedgfb".toSet(),display.wiresForEight)
        assertEquals("cdfgeb".toSet(),display.wiresForSix)
        assertEquals("fbcad".toSet(), display.wiresForThree)
        assertEquals("cdfbe".toSet(), display.wiresForFive)
        assertEquals("gcdfa".toSet(), display.wiresForTwo)
        assertEquals("cagedb".toSet(), display.wiresForZero)
        assertEquals("cefabd".toSet(), display.wiresForNine)
    }
    @Test
    fun `values of segments in test data`() {
        val display = parse(testData)[0]
        assertEquals(5353, display.valueOfEachSegments())
    }
    @Test
    fun `values of segments in sample data`() {
        assertEquals(61229, partTwo(sampleData))
    }
    @Test
    fun `part two with puzzle Input`() {
        assertEquals(973292, partTwo(puzzleInput))
    }
}