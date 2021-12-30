import java.lang.IllegalArgumentException

class Alu( _input:List<Int>, _lines:List<String> = listOf()) {
    val variables = mutableMapOf('w' to 0, 'x' to 0, 'y' to 0, 'z' to 0)
    private var inputStream:List<Int> = listOf()
    private var inputNdx = 0
    private var program = listOf<()->Unit>()

    init {
        inputStream = _input
        program = createProgram(_lines)
    }

    fun inp(a:Char) { variables[a] = inputStream[inputNdx++]}

    fun add(a:Char, b:Char) { add(a, variables.getValue(b))}
    private fun add(a:Char, b:Int) { variables[a] = variables.getValue(a) + b}

    fun mul(a:Char, b:Char) { mul(a, variables.getValue(b))}
    private fun mul(a:Char, b:Int) {variables[a] = variables.getValue(a) * b}

    fun div(a:Char, b:Char) { div(a, variables.getValue(b))}
    fun div(a:Char, b:Int) {
        if (b == 0) throw IllegalArgumentException("cannot divide by zero")
        variables[a] = variables.getValue(a) / b}

    fun mod(a:Char, b:Char) { mod(a, variables.getValue(b))}
    private fun mod(a:Char, b:Int) {
        if (variables.getValue(a) < 0) throw IllegalArgumentException("cannot mod a value if it is less than zero")
        if (b <= 0) throw IllegalArgumentException("cannot mod by a value if it is less than or equal to zero")
        variables[a] = variables.getValue(a) % b}

    fun eql(a:Char, b:Char) { eql(a, variables.getValue(b))}
    private fun eql(a:Char, b:Int) {variables[a] = if (variables.getValue(a) == b) 1 else 0}

    private fun createProgram(lines:List<String>) =
        lines.mapNotNull{
            when(it.substring(0..2)) {
                "inp" -> createInp(it)
                "add" -> createFunction(it, ::add, ::add )
                "mul" -> createFunction(it, ::mul, ::mul )
                "div" -> createFunction(it, ::div, ::div )
                "mod" -> createFunction(it, ::mod, ::mod )
                "eql" -> createFunction(it, ::eql, ::eql )
                else -> null
            }
        }

    private fun createInp(line:String): () -> Unit = { inp(line.parameter1) }

    private fun createFunction(line:String, f1:(Char, Char)->Unit, f2:(Char, Int)->Unit): ()->Unit {
        if (line.parameter2.toIntOrNull() != null) return { f2(line.parameter1, line.parameter2.toInt()) }
        else return { f1(line.parameter1, line.parameter2[0]) }
    }

    fun runProgram(input:List<Int>) {
        inputStream = input
        inputNdx = 0
        variables['w'] = 0
        variables['x'] = 0
        variables['y'] = 0
        variables['z'] = 0
        program.forEach { it() }
    }
}

//**** The stuff that actually calculates the solution ****//
data class PairedDigit(val aPosition:Int, var bPosition:Int? = null, var a:Int? = null, var b:Int? = null) {
    fun updateDigitValuesPartOne(diff: Int) {
        a = if (diff >= 0) 9 - diff else 9
        b = if (diff >= 0) 9 else 9 + diff
    }
    fun updateDigitValuesPartTwo(diff: Int) {
        a = if (diff >= 0) 1 else 1 - diff
        b = if (diff >= 0) 1 + diff else 1
    }
    fun differenceBetweenDigitValues(sourceCode:List<String>) = sourceCode[bPosition!! * SUB_PROGRAM_SIZE + B_VAL_OFFSET].parameter2.toInt() +
            sourceCode[aPosition * SUB_PROGRAM_SIZE + A_VAL_OFFSET].parameter2.toInt()
}

const val SUB_PROGRAM_SIZE = 18
const val STACK_OFFSET = 4
const val A_VAL_OFFSET = 15
const val B_VAL_OFFSET = 5

fun createPairedDigits(sourceCode:List<String>, digitUpdater:PairedDigit.(Int)->Unit):List<PairedDigit> {
    val pairedDigits = mutableListOf<PairedDigit>()
    sourceCode.chunked(SUB_PROGRAM_SIZE).forEachIndexed { ndx, subProgram ->
        if (subProgram[STACK_OFFSET].parameter2 == "1") pairedDigits.add(PairedDigit(ndx))
        else {
            pairedDigits.findLast { it.bPosition == null }?.apply {
                bPosition = ndx
                digitUpdater(differenceBetweenDigitValues(sourceCode))
            }
        }
    }
    return pairedDigits
}

fun List<PairedDigit>.toListOfInts() =
    flatMap{ listOf(Pair(it.aPosition,it.a),Pair(it.bPosition, it.b))}.sortedBy { it.first }.map{it.second as Int}

fun partOne(data:List<String>) = createPairedDigits(data, PairedDigit::updateDigitValuesPartOne).toListOfInts()
fun partTwo(data:List<String>) = createPairedDigits(data, PairedDigit::updateDigitValuesPartTwo).toListOfInts()

val String.parameter1 get()  = split(" ")[1][0] // e.g. with mul x 1 returns 'x'
val String.parameter2 get()  = split(" ")[2]   // e.g. with mul x 2 returns "2"