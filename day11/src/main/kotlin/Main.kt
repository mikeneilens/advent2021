
data class Position(val x:Int, val y:Int, private val maxX:Int, val maxY:Int ) {

    val surroundingPositions:List<Position> by lazy {
        (-1..1).flatMap { xOffset -> (-1..1).map{yOffset -> Position(x + xOffset, y + yOffset, maxX, maxY)} }
            .filter{ it.x in 0..maxX && it.y in 0..maxY && it != this}}
}

typealias OctopusMap = MutableMap<Position, Int>
val OctopusMap.positions  get() = keys
val OctopusMap.noOfOctopusThatHaveFlashed get() = values.count{it == 0}
val OctopusMap.allHaveFlashed get() = noOfOctopusThatHaveFlashed == positions.size

fun List<String>.parse():OctopusMap {
    val octopusMap = mutableMapOf<Position, Int>()
    forEachIndexed{y, row -> row.forEachIndexed{x, char ->
        octopusMap[Position(x,y, row.length -1, size -1)]= char.toString().toInt()
    }}
    return octopusMap
}

fun OctopusMap.increaseEnergyByOne() = positions.forEach(::incrementValue)

fun OctopusMap.incrementValue(position:Position) {  this[position] = getOrDefault(position, 0) + 1 }

fun OctopusMap.upDateOctopusSurroundingFlashers() {
    val flashedPositions = mutableListOf<Position>()
    while (positionsOfFlashersNotProcessed(flashedPositions).isNotEmpty() ) {
        positionsOfFlashersNotProcessed(flashedPositions).forEach{ position ->
            position.surroundingPositions.forEach (::incrementValue)
            flashedPositions.add(position)
        }
    }
}

fun OctopusMap.positionsOfFlashersNotProcessed(flashedPositions:List<Position>) = filter{it.value > 9 && !flashedPositions.contains(it.key)}.keys

fun OctopusMap.resetFlashers() {
    positions.filter{getValue(it) > 9 }.forEach { this[it] = 0}
}

fun OctopusMap.processStep():Int {
    increaseEnergyByOne()
    upDateOctopusSurroundingFlashers()
    resetFlashers()
    return noOfOctopusThatHaveFlashed
}

fun partOne(data:List<String>, steps:Int):Int {
    val octopusMap = data.parse()
    return (1..steps).sumOf { octopusMap.processStep() }
}

fun partTwo(data:List<String>):Int {
    val octopusMap = data.parse()
    var step = 0
    while (!octopusMap.allHaveFlashed) {
        step++
        octopusMap.processStep()
    }
    return step
}
