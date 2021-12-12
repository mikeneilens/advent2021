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

data class RuleOutcome(val nextCave:Cave, val includeCave:Boolean, val smallCaveVisitedAgain:Boolean )

typealias CaveFilterRule = (nextCave:Cave, route:List<Cave>, smallCaveVisitedAgain:Boolean ) -> RuleOutcome

fun partOne(data:List<String>):List<List<Cave>> = data.parse().findRoute("start", filterRule = ::filterRuleP1)

fun CaveMap.findRoute(cave: Cave, filterRule: CaveFilterRule, route: List<Cave> = listOf("start"), routes: List<List<Cave>> = listOf(), smallCaveVisitedAgain: Boolean = false):List<List<Cave>> =
    if (cave.isEndCave)  routes + listOf(route + cave)
    else adjacentCaves(cave)
        .map{filterRule(it, route, smallCaveVisitedAgain)}
        .filter {ruleOutcome -> ruleOutcome.includeCave}
        .flatMap{ findRoute(it.nextCave, filterRule, route + cave, routes, it.smallCaveVisitedAgain) }

fun filterRuleP1(nextCave:Cave, route:List<Cave>, smallCaveVisitedAgain:Boolean):RuleOutcome = when {
    (nextCave.isLarge) -> RuleOutcome(nextCave, true, smallCaveVisitedAgain)
    (nextCave.isStartCave)  -> RuleOutcome(nextCave,false, smallCaveVisitedAgain)
    (!route.contains(nextCave)) -> RuleOutcome(nextCave, true, smallCaveVisitedAgain)
    else ->  RuleOutcome(nextCave, false, smallCaveVisitedAgain)
}

fun filterRuleP2(nextCave:Cave, route:List<Cave>, smallCaveVisitedAgain:Boolean):RuleOutcome = when {
    (nextCave.isLarge) -> RuleOutcome(nextCave, true, smallCaveVisitedAgain)
    (nextCave.isStartCave)  -> RuleOutcome(nextCave,false, smallCaveVisitedAgain)
    (!route.contains(nextCave)) -> RuleOutcome(nextCave, true, smallCaveVisitedAgain)
    (route.contains(nextCave) && !smallCaveVisitedAgain ) -> RuleOutcome(nextCave, true, true)
    else ->  RuleOutcome(nextCave, false, smallCaveVisitedAgain)
}

fun partTwo(data:List<String>):List<List<Cave>> = data.parse().findRoute("start", filterRule = ::filterRuleP2)

