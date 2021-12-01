
data class AccumulatedResult (val previous:Int?, val noOfIncreases:Int)

fun compareCurrentToPrevious(accumulatedResult:AccumulatedResult, current:Int):AccumulatedResult {
    val (previous, noOfIncreases) = accumulatedResult
    return if (previous != null && current > previous) AccumulatedResult(current, noOfIncreases + 1) else AccumulatedResult(current, noOfIncreases)
}

fun partOne(listOfDepths:List<Int>) =
    listOfDepths.fold(AccumulatedResult(null, 0), ::compareCurrentToPrevious).noOfIncreases


fun List<Int>.convertListToSlidingWindow() = windowed(3,1).map{it.sum()}

fun partTwo(listOfDepths:List<Int>) = partOne(listOfDepths.convertListToSlidingWindow())
