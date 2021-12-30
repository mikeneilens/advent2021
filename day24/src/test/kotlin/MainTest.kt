import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `reading input into a variable`() {
        val alu = Alu(listOf(1, 2, 3))
        alu.inp('x')
        assertEquals(1, alu.variables['x'])
        alu.inp('y')
        assertEquals(2, alu.variables['y'])
    }

    @Test
    fun `add a to b `() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = 3
        alu.variables['z'] = 5
        alu.add('x', 'z')
        assertEquals(8, alu.variables['x'])
    }

    @Test
    fun `mul a by b `() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = 3
        alu.variables['z'] = 5
        alu.mul('x', 'z')
        assertEquals(15, alu.variables['x'])
    }

    @Test
    fun `div a by b `() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = 12
        alu.variables['z'] = 5
        alu.div('x', 'z')
        assertEquals(2, alu.variables['x'])
    }

    @Test
    fun `div a by zero `() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = 12
        alu.variables['z'] = 0
        try {
            alu.div('x', 'z')
            assertTrue(false, "should have thrown an illegal argument exception")
        } catch (e: IllegalArgumentException) {
            assertEquals(12, alu.variables['x'])
        }
    }

    @Test
    fun `mod a by b `() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = 12
        alu.variables['z'] = 5
        alu.mod('x', 'z')
        assertEquals(2, alu.variables['x'])
    }

    @Test
    fun `mod a value less than zero `() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = -12
        alu.variables['z'] = 5
        try {
            alu.mod('x', 'z')
            assertTrue(false, "should have thrown an illegal argument exception")
        } catch (e: IllegalArgumentException) {
            assertEquals(-12, alu.variables['x'])
        }
    }

    @Test
    fun `mod by a value that is zero `() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = 12
        alu.variables['z'] = 0
        try {
            alu.mod('x', 'z')
            assertTrue(false, "should have thrown an illegal argument exception")
        } catch (e: IllegalArgumentException) {
            assertEquals(12, alu.variables['x'])
        }
    }

    @Test
    fun `eql when a and b match`() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = 12
        alu.variables['z'] = 12
        alu.eql('x', 'z')
        assertEquals(1, alu.variables['x'])
    }

    @Test
    fun `eql when a and b do not match`() {
        val alu = Alu(listOf(1, 2, 3))
        alu.variables['x'] = 12
        alu.variables['z'] = 13
        alu.eql('x', 'z')
        assertEquals(0, alu.variables['x'])
    }

    @Test
    fun `running first sample program`() {
        val lines = """
            inp x
            mul x -1
        """.trimIndent().split("\n")
        val alu = Alu(listOf(), lines)
        alu.runProgram(listOf(5))
        assertEquals(-5, alu.variables['x'])
    }

    @Test
    fun `running sample program 2`() {
        val lines = """
            inp z
            inp x
            mul z 3
            eql z x
        """.trimIndent().split("\n")
        val alu = Alu(listOf(), lines)
        alu.runProgram(listOf(2, 5))
        assertEquals(0, alu.variables['z'])
        alu.runProgram(listOf(2, 6))
        assertEquals(1, alu.variables['z'])
    }

    @Test
    fun `running sample program 3`() {
        val lines = """
            inp w
            add z w
            mod z 2
            div w 2
            add y w
            mod y 2
            div w 2
            add x w
            mod x 2
            div w 2
            mod w 2
        """.trimIndent().split("\n")
        val alu = Alu(listOf(), lines)
        alu.runProgram(listOf("1010".toInt(2)))
        assertEquals(0, alu.variables['z'])
        assertEquals(1, alu.variables['y'])
        assertEquals(0, alu.variables['x'])
        assertEquals(1, alu.variables['w'])
        alu.runProgram(listOf("0101".toInt(2)))
        assertEquals(1, alu.variables['z'])
        assertEquals(0, alu.variables['y'])
        assertEquals(1, alu.variables['x'])
        assertEquals(0, alu.variables['w'])

    }

    @Test
    fun `running puzzle input with model number 13579246899999 - not surprisingly fails`() {
        val alu = Alu(listOf(), puzzleInput)
        try {
            alu.runProgram(listOf(1, 3, 5, 7, 9, 2, 4, 6, 8, 9, 9, 9, 9, 9))
        } catch (e: IllegalArgumentException) {
            assertEquals(-2066114392, alu.variables['z'])
        }
    }

    @Test
    fun `creating paired digits`() {
        val pairedDigits = createPairedDigits(puzzleInput, PairedDigit::updateDigitValuesPartOne)
        assertEquals(PairedDigit(0, 13, 9, 3), pairedDigits[0])
        assertEquals(PairedDigit(1, 12, 9, 7), pairedDigits[1])
        assertEquals(PairedDigit(2, 3, 2, 9), pairedDigits[2])
        assertEquals(PairedDigit(4, 5, 8, 9), pairedDigits[3])
        assertEquals(PairedDigit(6, 11, 9, 8), pairedDigits[4])
        assertEquals(PairedDigit(7, 10, 3, 9), pairedDigits[5])
        assertEquals(PairedDigit(8, 9, 1, 9), pairedDigits[6])
    }

    @Test
    fun `running puzzle input with potential solution for p1`() {
        val alu = Alu(listOf(), puzzleInput)
        alu.runProgram(listOf(9, 9, 2, 9, 8, 9, 9, 3, 1, 9, 9, 8, 7, 3))
        assertEquals(0, alu.variables['z'])
    }

    @Test
    fun `part one`() {
        assertEquals(listOf(9, 9, 2, 9, 8, 9, 9, 3, 1, 9, 9, 8, 7, 3), partOne(puzzleInput))
        println("Part one answer is ${partOne(puzzleInput).map{it.toString()}.joinToString("")}")

    }

    @Test
    fun `running puzzle input with potential solution for p2`() {
        val alu = Alu(listOf(), puzzleInput)
        alu.runProgram(listOf(7, 3, 1, 8, 1, 2, 2, 1, 1, 9, 7, 1, 1, 1))
        assertEquals(0, alu.variables['z'])
    }

    @Test
    fun `part two`() {
        assertEquals(listOf(7, 3, 1, 8, 1, 2, 2, 1, 1, 9, 7, 1, 1, 1), partTwo(puzzleInput))
        println("Part two answer is ${partTwo(puzzleInput).map{it.toString()}.joinToString("")}")
    }
}