
data class AccumulatedResult (val previous:Int, val noOfIncreases:Int)

fun compareCurrentToPrevious(accumulatedResult:AccumulatedResult, current:Int):AccumulatedResult {
    val (previous, noOfIncreases) = accumulatedResult
    return if (previous in 1 until current) AccumulatedResult(current, noOfIncreases + 1) else AccumulatedResult(current, noOfIncreases)
}

fun partOne(listOfDepths:List<Int>):Int =
    listOfDepths.fold(AccumulatedResult(0, 0), ::compareCurrentToPrevious).noOfIncreases


fun List<Int>.convertListToSlidingWindow():List<Int> = windowed(3,1).map{it.sum()}

fun partTwo(listOfDepths:List<Int>):Int = partOne(listOfDepths.convertListToSlidingWindow())
