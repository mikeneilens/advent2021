
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

fun Map<String, List<String>>.findRoute(start:String, end:String, route:List<String> = listOf("start"), routes:List<List<String>> = listOf()):List<List<String>> {
    return if (start == end)  routes + listOf(route + start)
    else getValue(start).filter{it == it.uppercase() || !(route.contains(it))}.flatMap{ findRoute(it, end, route + start, routes) }

}

fun partOne(data:List<String>):List<List<String>> = data.parse().findRoute("start","end")

