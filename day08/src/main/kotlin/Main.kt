data class Display(val signalWires:List<Set<Char>>, val segments:List<Set<Char>>) {
    fun segmentsThatAre(wires:Set<Char>) = segments.filter{it == wires}

    val wiresForEight = signalWires.first { it.size == 7 }
    val wiresForOne = signalWires.first { it.size == 2 }
    val wiresForFour = signalWires.first { it.size == 4 }
    val wiresForSeven = signalWires.first { it.size == 3 }
    val wiresForSix = signalWires.filter { it.size == 6 }.map {wires ->  Pair(wires, (wires - wiresForOne)) }.first { it.second.size == 5 }.first
    val wiresForThree = signalWires.filter { it.size == 5 }.map {wires-> Pair(wires, wires - wiresForOne) }.first { it.second.size == 3 }.first
    val wiresForFive = signalWires.filter { it.size == 5 && it != wiresForThree }.map {wires -> Pair(wires, wires - wiresForFour) }.first { it.second.size == 2 }.first
    val wiresForTwo = signalWires.first { it.size == 5 && it != wiresForThree && it != wiresForFive }
    val wiresForZero = signalWires.filter { it.size == 6 && it != wiresForSix }.map {wires -> Pair(wires, (wires - wiresForFive)) }.first { it.second.size == 2 }.first
    val wiresForNine = signalWires.first { it.size == 6 && it != wiresForSix && it != wiresForZero }

    private val decodedWires = mapOf(wiresForZero to 0, wiresForOne to 1, wiresForTwo to 2, wiresForThree to 3, wiresForFour to 4,
        wiresForFive to 5, wiresForSix to 6, wiresForSeven to 7, wiresForEight to 8, wiresForNine to 9)

    fun valueOfEachSegments() = segments.map{ segment ->  decodedWires.getValue( segment) }.toInt()
}

fun parse(data:List<String>):List<Display> =
    data.map {
        val (signals, segments) = it.split(" | ").toPair()
        Display(signals.split(" ").map(String::toSet), segments.split(" ").map(String::toSet))
    }

fun List<Display>.digitsContainingFourSevenEight() =
    flatMap{it.segmentsThatAre(it.wiresForOne) + it.segmentsThatAre(it.wiresForFour) + it.segmentsThatAre(it.wiresForSeven) + it.segmentsThatAre(it.wiresForEight)}

fun partOne(data:List<String>) = parse(data).digitsContainingFourSevenEight().size

fun partTwo(data:List<String>) = parse(data).sumOf(Display::valueOfEachSegments)

//helpers
fun List<String>.toPair() = listOf(get(0),get(1))
fun List<Int>.toInt() = joinToString("", transform = Int::toString).toInt() // converts "[1,2,3]" to integer 123
