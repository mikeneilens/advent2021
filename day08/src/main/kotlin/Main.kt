data class Display(val signalWires:List<String>, val segments:List<String>) {
    fun segmentsdThatAreOne() = segments.filter{it.length == 2}
    fun segmentsdThatAreFour() = segments.filter{it.length == 4}
    fun segmentsdThatAreSeven() = segments.filter{it.length == 3}
    fun segmentsdThatAreEight() = segments.filter{it.length == 7}

    fun eight() = signalWires.map(String::toSet).filter{it.size == 7}.first().toSet()
    fun one() = signalWires.map(String::toSet).filter{it.size == 2}.first().toSet()
    fun four() = signalWires.map(String::toSet).filter{it.size == 4}.first().toSet()
    fun seven() = signalWires.map(String::toSet).filter{it.size == 3}.first().toSet()
    fun six():Set<Char> = signalWires.map(String::toSet).filter{it.size == 6}.map{ Pair(it, (it - one())) }.filter{it.second.size == 5}.first().first
    fun three() = signalWires.map(String::toSet).filter{it.size == 5}.map{ Pair(it, it - one()) }.filter{it.second.size == 3}.first().first
    fun five() = signalWires.map(String::toSet).filter{it.size == 5}.filter{it != three()}.map{Pair(it, it - four())}.filter{it.second.size == 2}.first().first
    fun two() = signalWires.map(String::toSet).filter{it.size == 5 &&  it != three() && it != five()}.first()
    fun zero() = signalWires.map(String::toSet).filter{it.size == 6 && it != six()}.map{ Pair(it, (it - five())) }.filter{it.second.size == 2}.first().first
    fun nine() = signalWires.map(String::toSet).filter{it.size == 6 &&  it != six() && it != zero()}.first()

    val decoders = listOf(::zero, ::one, ::two, ::three, ::four, ::five, ::six, ::seven, ::eight, ::nine).asSequence()

    fun valuesOfSegments() = segments.map{segment ->  decoders.indexOfFirst { segment.toSet() == it()}}.map(Int::toString).joinToString("").toInt()
}

fun partTwo(data:List<String>) = parse(data).map(Display::valuesOfSegments).sum()

fun parse(data:List<String>):List<Display> =
    data.map {
        val (signals, segments) = it.split(" | ").toPair()
        Display(signals.split(" "), segments.split(" "))
    }

fun List<String>.toPair() = listOf(get(0),get(1))

fun digitsContainingFourSevenEight(displays:List<Display>) =
    displays.flatMap{it.segmentsdThatAreOne() + it.segmentsdThatAreFour() + it.segmentsdThatAreSeven() + it.segmentsdThatAreEight()}

fun partOne(data:List<String>):Int {
    val displays = parse(data)
    return digitsContainingFourSevenEight(displays).size
}