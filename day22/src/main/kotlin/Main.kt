import kotlin.math.abs

data class Cuboid(val switch:Boolean, val xRange:Range, val yRange:Range, val zRange:Range) {
    val volume:Long = abs(xRange.getLength() * yRange.getLength() * zRange.getLength())

    fun isInitializer() = xRange.isInitializer()  && yRange.isInitializer() && zRange.isInitializer()
}

data class Range(val start:Long, val finish:Long) {
    fun getLength() = finish - start + 1

    fun isInitializer() = start in -50L..50L && finish in -50L..50L

    inline fun forEach(action: (Long) -> Unit): Unit = (start..finish).forEach(action)
    inline fun <R>flatMap(transform: (Long) -> Iterable<R>): List<R> = (start..finish).flatMap(transform)
    inline fun <R>map(transform: (Long) -> R): List<R> = (start..finish).map(transform)
}

data class Position(val x:Long, val y:Long, val z:Long)

fun List<String>.parse() = map(String::toCuboid)

fun String.toCuboid() =
    Cuboid(startsWith("on"), toValues(param=1).toRange(),toValues(param=2).toRange(),toValues(param=3).toRange())

fun String.toValues(param:Int) = split("=")[param].split(",")[0].split("..").map(String::toLong)
fun List<Long>.toRange() =  Range(get(0),get(1))

fun List<String>.partOne():Map<Position, Boolean> {
    val map = mutableMapOf<Position,Boolean>()
    parse().filter(Cuboid::isInitializer).forEach { cuboid ->
          cuboid.xRange.forEach { x-> cuboid.yRange.forEach { y-> cuboid.zRange.forEach { z ->
                      map[Position(x,y,z)] = cuboid.switch
                } } } }
    return map
}

fun List<Cuboid>.partTwo():List<Cuboid> =
    drop(1).fold(listOf(first())){total, cuboid ->
        if (cuboid.switch) subtract(total, cuboid) + cuboid else subtract(total, cuboid)
    }

//Subtracts a range from another ranges giving the ranges that are left over and the ranges that were removed.
tailrec fun subtract(line:Range, otherLine:Range, rangesToKeep:List<Range> = listOf(),rangesRemoved:List<Range> = listOf()):Pair<List<Range>,List<Range>> {
    return when {
        line.finish < otherLine.start -> Pair(rangesToKeep + listOf(line),rangesRemoved) //line ends before the other line starts
        line.start > otherLine.finish -> Pair(rangesToKeep + listOf(line),rangesRemoved) //line starts after the other line ends
        line.start >= otherLine.start && line.finish <= otherLine.finish -> Pair(rangesToKeep,rangesRemoved + listOf(line)) //line is inside the other line
        line.start < otherLine.start ->  //line starts before the other line so keep the start of the line and remove the end of the line
            subtract(Range(otherLine.start,line.finish), otherLine, rangesToKeep + listOf(Range(line.start , otherLine.start -1)),rangesRemoved)
        else -> //line starts after the other line so keep the end of the line and remove the start of the line
            subtract(Range(otherLine.finish+1, line.finish), otherLine, rangesToKeep, rangesRemoved + listOf(Range(line.start, otherLine.finish)) )
    }
}

fun subtract(cuboids:List<Cuboid>, otherCuboid:Cuboid):List<Cuboid> {
    val halvesForEachCuboidX = cuboids.map{remainingCuboid -> Pair(remainingCuboid,subtract(remainingCuboid.xRange, otherCuboid.xRange))}
    val cuboidsToKeepX = halvesForEachCuboidX.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.first.map { xRange -> Cuboid(remainingCuboid.switch, xRange, remainingCuboid.yRange, remainingCuboid.zRange)  } }
    val remainingCuboidsX = halvesForEachCuboidX.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.second.map { xRange -> Cuboid(remainingCuboid.switch, xRange, remainingCuboid.yRange, remainingCuboid.zRange)  } }

    val halvesForEachCuboidY = remainingCuboidsX.map{remainingCuboid -> Pair(remainingCuboid,subtract(remainingCuboid.yRange, otherCuboid.yRange))}
    val cuboidsToKeepY = halvesForEachCuboidY.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.first.map { yRange -> Cuboid(remainingCuboid.switch, remainingCuboid.xRange, yRange, remainingCuboid.zRange)  } }
    val remainingCuboidsY = halvesForEachCuboidY.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.second.map { yRange -> Cuboid(remainingCuboid.switch, remainingCuboid.xRange, yRange, remainingCuboid.zRange)  } }

    val halvesForEachCuboidZ = remainingCuboidsY.map{remainingCuboid -> Pair(remainingCuboid,subtract(remainingCuboid.zRange, otherCuboid.zRange))}
    val cuboidsToKeepZ = halvesForEachCuboidZ.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.first.map { zRange -> Cuboid(remainingCuboid.switch, remainingCuboid.xRange, remainingCuboid.yRange, zRange)  } }
    return cuboidsToKeepX + cuboidsToKeepY + cuboidsToKeepZ
}
