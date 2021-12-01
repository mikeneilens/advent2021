
data class AccumulatedResult (val previous:Int, val noOfIncreases:Int)

fun compareCurrentToPrevious(accumulatedResult:AccumulatedResult, current:Int):AccumulatedResult {
    val (previous, noOfIncreases) = accumulatedResult
    return if (current > previous && previous > 0) AccumulatedResult(current, noOfIncreases + 1) else AccumulatedResult(current, noOfIncreases)
}

fun partOne(listOfDepths:List<Int>):Int {
    return listOfDepths.fold(AccumulatedResult(0, 0), ::compareCurrentToPrevious).noOfIncreases
}