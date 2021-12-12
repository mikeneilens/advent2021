fun List<String>.parse():Map<String, List<String>> {
    val map = mutableMapOf<String, List<String>>()
    forEach{line ->
        val (p1, p2) = line.split("-")
        val existingValuesForP1 = map.getOrDefault(p1, listOf())
        map[p1] = existingValuesForP1 + p2
        val existingValuesForP2 = map.getOrDefault(p2, listOf())
        map[p2] = existingValuesForP2 + p1

    }
    return map
}

fun Map<String, List<String>>.findRoute(start:String, route:List<String> = listOf("start"), routes:List<List<String>> = listOf()):List<List<String>> {
    return if (start == "end")  routes + listOf(route + start)
    else getValue(start).filter{it == it.uppercase() || !(route.contains(it))}.flatMap{ findRoute(it, route + start, routes) }

}

fun partOne(data:List<String>):List<List<String>> = data.parse().findRoute("start")

fun Map<String, List<String>>.findRoute2(start:String, route:List<String> = listOf("start"), routes:List<List<String>> = listOf(),smallCaveVisitedAgain:Boolean):List<List<String>> {
    return if (start == "end")  routes + listOf(route + start)
    //else getValue(start).filter{it == it.uppercase() || !(route.contains(it))}.flatMap{ findRoute2(it, route + start, routes) }
    else getValue(start).map{filterRule(it, route, smallCaveVisitedAgain)}.filter {it.include}  .flatMap{ findRoute2(it.start, route + start, routes, it.smallCaveVisitedAgain) }
}

data class RuleOutcome(val start:String, val include:Boolean, val smallCaveVisitedAgain:Boolean )

fun filterRule(nextCave:String, route:List<String>, smallCaveVisitedAgain:Boolean):RuleOutcome = when {
    (nextCave == nextCave.uppercase()) -> RuleOutcome(nextCave, true, smallCaveVisitedAgain)
    (nextCave == "start")  -> RuleOutcome(nextCave,false, smallCaveVisitedAgain)
    (!route.contains(nextCave)) -> RuleOutcome(nextCave, true, smallCaveVisitedAgain)
    (route.contains(nextCave) && !smallCaveVisitedAgain ) -> RuleOutcome(nextCave, true, true)
    else ->  RuleOutcome(nextCave, false, smallCaveVisitedAgain)
}

fun partTwo(data:List<String>):List<List<String>> = data.parse().findRoute2("start",  smallCaveVisitedAgain = false )

