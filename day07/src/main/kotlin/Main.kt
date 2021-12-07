import kotlin.math.abs

fun parse(data:String) = data.split(",").map(String::toInt).groupingBy { it }.eachCount()

fun calcDistanceForEachPosition(crabsAtEachPosition:Map<Int,Int>, costForEachStepSize:List<Int>): List<Pair<Int, Int>> =
    (0..crabsAtEachPosition.keys.maxOf { it }).map{ position -> Pair(position, costOfMovingCrabs(position, crabsAtEachPosition, costForEachStepSize))}

fun costOfMovingCrabs(position: Int, crabsAtEachPosition: Map<Int, Int>, costForEachStepSize: List<Int>) =
    crabsAtEachPosition.keys.sumOf { crabPosition ->
        costForEachStepSize[abs(position - crabPosition)] * crabsAtEachPosition.getValue(crabPosition)
    }

fun partOne(data:String):Int {
    val values = parse(data)
    val costForStepSize = (0..values.keys.maxOf{it}).toList() // returns [0,1,2,3,4,5] etc
    return calcDistanceForEachPosition(values,costForStepSize).minOf { it.second }
}
fun partTwo(data:String):Int {
    val values = parse(data)
    val costForStepSize = (1..values.keys.maxOf{it}).fold(listOf(0)){ a, v -> a + (v + a.last())} //returns [0,1,3,6,10,15,21,28] etc
    return calcDistanceForEachPosition(values,costForStepSize).minOf { it.second }
}