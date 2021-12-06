fun partOne(data:String, days:Int):Long {
    var fishForEachAge = turnListOfNumbersIntoMap(data)
    repeat(days) {
        fishForEachAge = ((1..8) + 0).mapIndexed {newAge,oldAge -> calcFishCount(oldAge, newAge, fishForEachAge) }.toMap()
    }
    return fishForEachAge.values.sumOf{it}
}

fun calcFishCount(oldAge: Int, newAge: Int, fishForEachAge: Map<Int, Long>) =
    if (newAge == 6) Pair(6, newFishAgedSix(fishForEachAge)) else Pair(newAge, fishForEachAge.getOrDefault(oldAge, 0))

fun newFishAgedSix(fishMap: Map<Int, Long>) =
    fishMap.getOrDefault(7,0) + fishMap.getOrDefault(0,0)

fun turnListOfNumbersIntoMap(data:String):Map<Int, Long> =
    data.split(",")
        .map(String::toInt)
        .groupingBy { it }.eachCount()
        .toList().associate { Pair(it.first, it.second.toLong()) }