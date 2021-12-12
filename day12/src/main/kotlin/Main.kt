typealias Cave = String
typealias CaveMap = Map<Cave, List<Cave>>

fun CaveMap.adjacentCaves(cave:Cave) = getOrDefault(cave, listOf())

fun List<String>.parse():CaveMap {
    val map = mutableMapOf<Cave, List<Cave>>()
    forEach{line ->
        val (cave1, cave2) = line.split("-")
        map[cave1] = map.adjacentCaves(cave1) + cave2
        map[cave2] = map.adjacentCaves(cave2) + cave1
    }
    return map
}

val Cave.isLarge get() = equals(uppercase())
val Cave.isStartCave get() = equals("start")
val Cave.isEndCave get() = equals("end")

data class CaveDecision(val nextCave:Cave, val includeCave:Boolean, val smallCaveVisitedAgain:Boolean = false )

typealias CaveDecider = (nextCave:Cave, route:List<Cave>, smallCaveVisitedAgain:Boolean ) -> CaveDecision

fun partOne(data:List<String>):List<List<Cave>> = data.parse().findRoute("start", caveDeciderP1)

fun CaveMap.findRoute(cave:Cave, caveDecider:CaveDecider, route:List<Cave> = listOf("start"), routes:List<List<Cave>> = listOf(), smallCaveVisitedAgain:Boolean = false):List<List<Cave>> =
    if (cave.isEndCave)  routes + listOf(route + cave)
    else adjacentCaves(cave)
        .map{adjacentCave -> caveDecider(adjacentCave, route, smallCaveVisitedAgain)}
        .filter {caveDecision -> caveDecision.includeCave}
        .flatMap{caveDecision ->  findRoute(caveDecision.nextCave, caveDecider, route + cave, routes, caveDecision.smallCaveVisitedAgain) }

val caveDeciderP1:CaveDecider = { nextCave, route, _ ->
    if (nextCave.isLarge || nextCave !in route)  CaveDecision(nextCave, includeCave = true)
    else  CaveDecision(nextCave, includeCave = false)
 }

fun partTwo(data:List<String>):List<List<Cave>> = data.parse().findRoute("start", caveDeciderP2)

val caveDeciderP2:CaveDecider = { nextCave, route, smallCaveVisitedAgain -> when {
    nextCave.isLarge -> CaveDecision(nextCave, includeCave = true, smallCaveVisitedAgain = smallCaveVisitedAgain)
    nextCave.isStartCave -> CaveDecision(nextCave, includeCave = false, smallCaveVisitedAgain = smallCaveVisitedAgain)
    nextCave !in route -> CaveDecision(nextCave, includeCave = true, smallCaveVisitedAgain = smallCaveVisitedAgain)
    (nextCave in route) && !smallCaveVisitedAgain -> CaveDecision(nextCave, includeCave = true, smallCaveVisitedAgain = true)
    else ->  CaveDecision(nextCave, includeCave = false, smallCaveVisitedAgain = smallCaveVisitedAgain)
}}


