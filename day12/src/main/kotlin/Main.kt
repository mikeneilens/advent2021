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

data class CaveDecision(val nextCave:Cave, val includeCave:Boolean, val smallCaveVisitedAgain:Boolean )

typealias CaveDecider = (nextCave:Cave, route:List<Cave>, smallCaveVisitedAgain:Boolean ) -> CaveDecision

fun partOne(data:List<String>):List<List<Cave>> = data.parse().findRoute("start", caveDecider = ::caveDeciderP1)

fun CaveMap.findRoute(cave:Cave, caveDecider:CaveDecider, route:List<Cave> = listOf("start"), routes:List<List<Cave>> = listOf(), smallCaveVisitedAgain:Boolean = false):List<List<Cave>> =
    if (cave.isEndCave)  routes + listOf(route + cave)
    else adjacentCaves(cave)
        .map{caveDecider(it, route, smallCaveVisitedAgain)}
        .filter {ruleOutcome -> ruleOutcome.includeCave}
        .flatMap{ruleOutcome ->  findRoute(ruleOutcome.nextCave, caveDecider, route + cave, routes, ruleOutcome.smallCaveVisitedAgain) }

fun caveDeciderP1(nextCave:Cave, route:List<Cave>, smallCaveVisitedAgain:Boolean):CaveDecision =
    if (nextCave.isLarge || !route.contains(nextCave))  CaveDecision(nextCave, includeCave = true, smallCaveVisitedAgain = smallCaveVisitedAgain)
    else  CaveDecision(nextCave, includeCave = false, smallCaveVisitedAgain = smallCaveVisitedAgain)

fun caveDeciderP2(nextCave:Cave, route:List<Cave>, smallCaveVisitedAgain:Boolean):CaveDecision = when {
    (nextCave.isLarge) -> CaveDecision(nextCave, includeCave = true, smallCaveVisitedAgain = smallCaveVisitedAgain)
    (nextCave.isStartCave)  -> CaveDecision(nextCave, includeCave = false, smallCaveVisitedAgain = smallCaveVisitedAgain)
    (!route.contains(nextCave)) -> CaveDecision(nextCave, includeCave = true, smallCaveVisitedAgain = smallCaveVisitedAgain)
    (route.contains(nextCave) && !smallCaveVisitedAgain ) -> CaveDecision(nextCave, includeCave = true, smallCaveVisitedAgain = true)
    else ->  CaveDecision(nextCave, includeCave = false, smallCaveVisitedAgain = smallCaveVisitedAgain)
}

fun partTwo(data:List<String>):List<List<Cave>> = data.parse().findRoute("start", caveDecider = ::caveDeciderP2)

