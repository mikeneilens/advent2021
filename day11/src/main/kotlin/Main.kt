
data class Position(val x:Int, val y:Int ) {

    fun surroundingPositions(maxX:Int, maxY:Int):List<Position> =
        listOf(Position(x - 1, y),Position(x , y - 1),Position(x + 1, y),Position(x , y + 1),
            Position(x - 1, y - 1),Position(x + 1, y - 1),Position(x - 1, y + 1),Position(x + 1, y + 1)
        )
            .filter{ it.x in 0..maxX && it.y in 0..maxY}
}

typealias OctopusMap = MutableMap<Position, Int>
val OctopusMap.positions  get() = keys
val OctopusMap.maxX  get() =  positions.maxOf { it.x }
val OctopusMap.maxY  get() =  positions.maxOf { it.y }

fun List<String>.parse():OctopusMap {
    val octopusMap = mutableMapOf<Position, Int>()
    forEachIndexed{y, row -> row.forEachIndexed{x, char ->
        octopusMap[Position(x,y)]= char.toString().toInt()
    }}
    return octopusMap
}

fun OctopusMap.increaseEnergyByOne() = positions.forEach(::incrementValue)

fun OctopusMap.incrementValue(position:Position) {  this[position] = getOrDefault(position, 0) + 1 }

fun OctopusMap.upDateOctopusSurroundingFlashers() {
    val flashedPositions = mutableListOf<Position>()
    while (positionsOfFlashersNotProcessed(flashedPositions).isNotEmpty() ) {
        positionsOfFlashersNotProcessed(flashedPositions).forEach{ position ->
            position.surroundingPositions(maxX,maxY).forEach (::incrementValue)
            flashedPositions.add(position)
        }
    }
}

fun OctopusMap.positionsOfFlashersNotProcessed(flashedPositions:List<Position>) = filter{it.value > 9 && !flashedPositions.contains(it.key)}.keys

fun OctopusMap.resetFlashers() {
    positions.filter{getValue(it) > 9 }.forEach { this[it] = 0}
}

fun OctopusMap.noOfOctopusThatHaveFlashed() = values.count{it == 0}

fun OctopusMap.processStep():Int {
    increaseEnergyByOne()
    upDateOctopusSurroundingFlashers()
    resetFlashers()
    return noOfOctopusThatHaveFlashed()
}

fun partOne(data:List<String>, steps:Int):Int {
    val octopusMap = data.parse()
    return (0 until steps).sumOf { octopusMap.processStep() }
}

fun partTwo(data:List<String>):Int {
    val octopusMap = data.parse()
    var step = 0
    while (!octopusMap.values.all { it == 0 }) {
        step++
        octopusMap.processStep()
    }
    return step
}
