data class Display(val signalWires:List<Set<Char>>, val segments:List<Set<Char>>) {
    fun segmentsThatAre(wires:Set<Char>) = segments.filter{it == wires}

    val eight = signalWires.first { it.size == 7 }
    val one = signalWires.first { it.size == 2 }
    val four = signalWires.first { it.size == 4 }
    val seven = signalWires.first { it.size == 3 }
    val six = signalWires.filter { it.size == 6 }.map { Pair(it, (it - one)) }.first { it.second.size == 5 }.first
    val three = signalWires.filter { it.size == 5 }.map { Pair(it, it - one) }.first { it.second.size == 3 }.first
    val five = signalWires.filter { it.size == 5 }.filter { it != three }.map { Pair(it, it - four) }.first { it.second.size == 2 }.first
    val two = signalWires.first { it.size == 5 && it != three && it != five }
    val zero = signalWires.filter { it.size == 6 && it != six }.map { Pair(it, (it - five)) }.first { it.second.size == 2 }.first
    val nine = signalWires.first { it.size == 6 && it != six && it != zero }

    private val decodedWires = mapOf(zero to 0, one to 1, two to 2, three to 3, four to 4, five to 5, six to 6, seven to 7, eight to 8, nine to 9)

    fun valueOfEachSegments() = segments.map{ segment ->  decodedWires.getValue( segment) }.toInt()
}

fun parse(data:List<String>):List<Display> =
    data.map {
        val (signals, segments) = it.split(" | ").toPair()
        Display(signals.split(" ").map(String::toSet), segments.split(" ").map(String::toSet))
    }

fun List<Display>.digitsContainingFourSevenEight() =
    flatMap{it.segmentsThatAre(it.one) + it.segmentsThatAre(it.four) + it.segmentsThatAre(it.seven) + it.segmentsThatAre(it.eight)}

fun partOne(data:List<String>) = parse(data).digitsContainingFourSevenEight().size

fun partTwo(data:List<String>) = parse(data).sumOf(Display::valueOfEachSegments)

//helpers
fun List<String>.toPair() = listOf(get(0),get(1))
fun List<Int>.toInt() = joinToString("", transform = Int::toString).toInt() // converts "[1,2,3]" to integer 123
