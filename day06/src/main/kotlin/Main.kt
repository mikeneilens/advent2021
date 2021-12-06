
fun partOne(data:String, days:Int):Long {
    var fishForEachAge = turnListOfNumbersIntoMap(data)
    repeat(days) {
        val newFishAges = ((1..8) + 0).mapIndexed {newAge,oldAge ->Pair(newAge,fishForEachAge[oldAge])}.toMap().toMutableMap()
        newFishAges[6] = newFishAgedSix(fishForEachAge)
        fishForEachAge = newFishAges
    }
    return fishForEachAge.values.filterNotNull().sumOf{it}
}

fun newFishAgedSix(fishMap: Map<Int, Long?>) =
    if (fishMap[7] == null && fishMap[0] == null) null
    else if (fishMap[7] != null && fishMap[0] != null) fishMap[7]!! + fishMap[0]!!
    else if (fishMap[7] != null) fishMap[7]
    else fishMap[0]

fun turnListOfNumbersIntoMap(data:String):Map<Int, Long?> =
    data.split(",")
        .map(String::toInt)
        .groupingBy { it }.eachCount()
        .toList().associate { Pair(it.first, it.second.toLong()) }
