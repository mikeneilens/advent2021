import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `doing a simple snail fish addition`() {
        assertEquals( "[[1,2],[[3,4],5]]",  (parse("[1,2]",null) + parse("[[3,4],5]",null)).text())
    }

    @Test
    fun `parsing simple`() {
        val expression = "[1,4]"
        val snailFish = parse(expression,null)
        assertEquals(1, ((snailFish as SFPair).p1 as RegularNumber).num)
        assertEquals(4, (snailFish.p2 as RegularNumber).num)
    }
    @Test
    fun `parsing regular number paired with a pair`() {
        val expression = "[[1,2],3]"
        val snailFish = parse(expression, null)
        assertEquals(1,(((snailFish as SFPair).p1 as SFPair).p1 as RegularNumber).num)
        assertEquals(2,(((snailFish ).p1 as SFPair).p2 as RegularNumber).num)
        assertEquals(3,((snailFish ).p2 as RegularNumber).num)

    }
    @Test
    fun `parsing pair paired with a regular number`() {
        val expression = "[9,[8,7]]"
        val snailFish = parse(expression, null)
        assertEquals(9,(((snailFish as SFPair).p1 as RegularNumber).num))
        assertEquals(8,(((snailFish ).p2 as SFPair).p1 as RegularNumber).num)
        assertEquals(7,(((snailFish ).p2 as SFPair).p2 as RegularNumber).num)
    }
    @Test
    fun `parsing pair paired with a pair`() {
        val expression = "[[1,9],[8,5]]"
        val snailFish = parse(expression, null)
        assertEquals(1,(((snailFish as SFPair).p1 as SFPair).p1 as RegularNumber).num)
        assertEquals(9,(((snailFish ).p1 as SFPair).p2 as RegularNumber).num)
        assertEquals(8,(((snailFish as SFPair).p2 as SFPair).p1 as RegularNumber).num)
        assertEquals(5,(((snailFish ).p2 as SFPair).p2 as RegularNumber).num)
    }
    @Test
    fun `parsing something complicated and seeing if it converts back to text`() {
        val expression = "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]"
        val snailFish = parse(expression, null)
        assertEquals("[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]", snailFish.text())
    }
    @Test
    fun `exploding example 1`() {
        val snailfish = parse( "[[[[[9,8],1],2],3],4]",null)
        val (exploder, left, right, _) = findExplodersAndSplitters(snailfish)
        exploder?.apply { explode(this, left,right) }
        assertEquals("[[[[0,9],2],3],4]", snailfish.text())
    }

    @Test
    fun `exploding example 2`() {
        val snailfish = parse( "[7,[6,[5,[4,[3,2]]]]]",null)
        val (exploder, left, right, _) = findExplodersAndSplitters(snailfish)
        exploder?.apply { explode(this, left,right) }
        assertEquals("[7,[6,[5,[7,0]]]]", snailfish.text())
    }
    @Test
    fun `exploding example 3`() {
        val snailfish = parse( "[[6,[5,[4,[3,2]]]],1]",null)
        val (exploder, left, right, _) = findExplodersAndSplitters(snailfish)
        exploder?.apply { explode(this, left,right) }
        assertEquals("[[6,[5,[7,0]]],3]", snailfish.text())
    }
    @Test
    fun `exploding example 4`() {
        val snailfish = parse( "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", null)
        val (exploder, left, right, _) = findExplodersAndSplitters(snailfish)
        exploder?.apply { explode(this, left,right) }
        assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", snailfish.text())
    }
    @Test
    fun `exploding example 5`() {
        val snailfish = parse( "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", null)
        val (exploder, left, right, _) = findExplodersAndSplitters(snailfish)
        exploder?.apply { explode(this, left,right) }
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", snailfish.text())
    }
    @Test
    fun `splitting a snailFish containing a splitter with an even split`() {
        val snailfish = SFPair(RegularNumber(0,null),RegularNumber(0,null),null)
        snailfish.p1 = RegularNumber(9,snailfish)
        snailfish.p2 = RegularNumber(10,snailfish)
        val (_,_,_, splitter) = findExplodersAndSplitters(snailfish)
        splitter?.apply { split(this) }
        assertEquals("[9,[5,5]]", snailfish.text())
    }
    @Test
    fun `splitting a snailFish containing a splitter with an odd split`() {
        val snailfish = SFPair(RegularNumber(0,null),RegularNumber(0,null),null)
        snailfish.p1 = RegularNumber(11,snailfish)
        snailfish.p2 = RegularNumber(10,snailfish)
        val (_,_,_, splitter) = findExplodersAndSplitters(snailfish)
        splitter?.apply { split(this) }
        assertEquals("[[5,6],10]", snailfish.text())
    }
    @Test
    fun `explodning and splitting until done`() {
        val snailfish = parse( "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", null)
        explodeAndSplit(snailfish)
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", snailfish.text())
    }

    @Test
    fun `summing list of snailFish`() {
        val list = """
            [1,1]
            [2,2]
            [3,3]
            [4,4]
            [5,5]
            [6,6]
        """.trimIndent().split("\n").map{parse(it,null)}
        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", list.sum().text())
    }
    @Test
    fun `summing long list of snailFish`() {
        val result = parse("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",null) + parse("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",null)
        println(result.text())

        println()
        val list = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
        """.trimIndent().split("\n").map{parse(it,null)}
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", list.sum().text())
    }
    @Test
    fun `magnitude of something complicated`() {
        val magnitude = parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]] becomes 3488",null).magnitude()
        assertEquals(3488, magnitude)
    }
    @Test
    fun `magnitude of sample data added together`() {
        val sample = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent().split("\n")
        val magnitude = partOne(sample)
        assertEquals(4140, magnitude)
    }
    @Test
    fun `partOne with puzzle input`() {
        val magnitude = partOne(puzzleInput)
        assertEquals(3699, magnitude)
    }
}