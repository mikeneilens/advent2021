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
    @Test
    fun `expression is complete by adding particular characters `() {
        val result1 = validateExpression("[({(<(())[]>[[{[]{<()<>>") as Result.OK
        assertEquals("}}]])})]" , result1.stack.reversed())
        val result2 = validateExpression("[(()[<>])]({[<{<<[]>>(") as Result.OK
        assertEquals(")}>]})" , result2.stack.reversed())
    }
    @Test
    fun `score of characters required to complete an expression`(){
        assertEquals(288957,"}}]])})]".calcScore())
        assertEquals(5566,")}>]})".calcScore())
        assertEquals(1480781,"}}>}>))))".calcScore())
        assertEquals(995444,"]]}}]}]}>".calcScore())
        assertEquals(294,"])}>".calcScore())
    }
    @Test
    fun `middle of scores for SampleData`() {
        assertEquals(288957L, partTwo(sampleData))
    }
    @Test
    fun `middle of scores for PuzzleInput`() {
        assertEquals(1605968119L, partTwo(puzzleInput))
    }
}