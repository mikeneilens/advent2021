data class TargetArea(val minX:Int, val maxX:Int, val minY:Int, val maxY:Int) {
    fun xIsInTarget(x:Int) = x >= minX && x <= maxX
    fun yIsInTarget(y:Int) = y >= minY && y <= maxY
    fun isInTarget(x:Int, y:Int) = xIsInTarget(x) && yIsInTarget(y)
}

fun String.parse():TargetArea {
    val (minX, maxX) = split("x=")[1].split(",")[0].parseRange()
    val (minY,maxY) = split("y=")[1].parseRange()
    return TargetArea(minX, maxX, minY, maxY)
}

typealias MoveRule = (velocity:Int, positiion:Int) -> Pair<Int, Int>

val moveX:MoveRule = {x:Int, positionX:Int ->  Pair(if (x > 0) x -1 else if (x < 0) x + 1 else x, positionX + x)}
val moveY:MoveRule = {y:Int, positionY:Int ->  Pair(y -1, positionY + y)}

typealias NotPassedOrHit = (velocity:Int, position:Int, min:Int, max:Int ) -> Boolean

fun TargetArea.xValuesInRange():List<Int> = vectorsInRange(0, maxX, minX, maxX, moveX, xNotPassedOrHit)
fun TargetArea.yValuesInRange():List<Int> = vectorsInRange(minY,9999, minY,maxY, moveY, yNotPassedOrHit)

fun vectorsInRange(initialVector:Int,maxVector:Int, min:Int, max:Int, moveRule:MoveRule, notPassedOrHitRule:NotPassedOrHit):List<Int> {
    val validVectors = mutableListOf<Int>()
    var vector = initialVector
    while (vector <= maxVector) {
        val endingPosition = moveUntilInTargetOrMisses(vector, min,max,moveRule,notPassedOrHitRule)
        if(endingPosition in min..max) validVectors.add(vector)
        vector++
    }
    return validVectors
}

fun moveUntilInTargetOrMisses(initialVelocity:Int, minPosition:Int, maxPosition:Int, moveRule:MoveRule, notPassedOrHit:NotPassedOrHit ):Int {
    var velocity = initialVelocity
    var position = 0
    while (notPassedOrHit(velocity, position, minPosition, maxPosition)) {
        val resultOfMove = moveRule(velocity, position)
        velocity = resultOfMove.first
        position = resultOfMove.second
    }
    return position
}

val  xNotPassedOrHit:NotPassedOrHit = { velocity:Int, position:Int, min:Int, max:Int -> velocity != 0   && !(position in min..max) && velocity <= max}
val  yNotPassedOrHit:NotPassedOrHit = { velocity:Int, position:Int, min:Int, max:Int -> !(position in min..max) && velocity >= min}

fun TargetArea.velocityHitsTarget(initialX:Int, initialY:Int):Pair<Boolean, Int> {
    var x = initialX
    var y = initialY
    var xPosition = 0
    var yPosition = 0
    var maxYPosition = Int.MIN_VALUE
    while(!(isInTarget(xPosition, yPosition)) && yPosition >= minY && xPosition <= maxX  ) {
        val resultOfXMove = moveX(x, xPosition)
        x = resultOfXMove.first
        xPosition = resultOfXMove.second
        yPosition = yPosition + y
        if (y > 0) maxYPosition = yPosition
        y--
    }
    return Pair( isInTarget(xPosition,yPosition), maxYPosition)
}

fun String.parseRange() = Pair(split("..")[0].toInt(), split("..")[1].toInt())

fun partOne(data:String):Int {
    val target = data.parse()
    val validX = target.xValuesInRange()
    val validY = target.yValuesInRange()
    val (_, height) = target.velocityHitsTarget(validX.first(), validY.last())
    return height
}
fun partTwo(data:String):Int {
    val target = data.parse()
    val validX = target.xValuesInRange()
    val validY = target.yValuesInRange()
    return  validX.sumOf{ x -> validY.count {y-> target.velocityHitsTarget(x,y).first } }
}