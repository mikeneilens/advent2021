import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

val sampleData = """
    [({(<(())[]>[[{[]{<()<>>
    [(()[<>])]({[<{<<[]>>(
    {([(<{}[<>[]}>{[]{[(<()>
    (((({<>}<{<{<>}{[]{[]{}
    [[<[([]))<([[{}[[()]]]
    [{[{({}]{}}([{[{{{}}([]
    {<[[]]>}<{[{[{[]{()[[[]
    [<(<(<(<{}))><([]([]()
    <{([([[(<>()){}]>(<<{{
    <{([{{}}[<[[[<>{}]]]>[]]
""".trimIndent().split("\n")

class MainTest {
    @Test
    fun `test validating with test data`() {
        val result0 = validateExpression("<[[]]>}<{[{[{[]{()[[[]")
        assertTrue(result0 is Result.OK)

        val result1 = validateExpression("{([(<{}[<>[]}>{[]{[(<()>")
        assertEquals('}',(result1 as Result.Error).illegalChar)
        val result2 = validateExpression("[[<[([]))<([[{}[[()]]]")
        assertEquals(')',(result2 as Result.Error).illegalChar)
        val result3 = validateExpression("[{[{({}]{}}([{[{{{}}([]")
        assertEquals(']',(result3 as Result.Error).illegalChar)
        val result4 = validateExpression("[<(<(<(<{}))><([]([]()")
        assertEquals(')',(result4 as Result.Error).illegalChar)
        val result5 = validateExpression("<{([([[(<>()){}]>(<<{{")
        assertEquals('>',(result5 as Result.Error).illegalChar)
    }
    @Test
    fun `test validating sample data`() {
        val result = sampleData.findErrors()
        assertEquals(1, result.count { it.illegalChar == ']' } )
        assertEquals(1, result.count { it.illegalChar == '}' } )
        assertEquals(1, result.count { it.illegalChar == '>' } )
        assertEquals(2, result.count { it.illegalChar == ')' } )
    }
    @Test
    fun `part one with sample data`() {
        val result = partOne(sampleData)
        assertEquals(26397, result)
    }
    @Test
    fun `part one with puzzle input`() {
        val result = partOne(puzzleInput)
        assertEquals(392043, result)
    }
}