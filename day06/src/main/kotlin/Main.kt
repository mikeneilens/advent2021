fun partOne(data:String, days:Int):Long =
    (1..days).fold(parse(data),::makeFishOlder).values.sumOf{it}

fun makeFishOlder(fishForEachAge: Map<Int, Long>, day:Int) =
    ((1..8) + 0).mapIndexed { newAge, oldAge -> Pair(newAge, calcFishCount(oldAge, newAge, fishForEachAge)) }.toMap()

fun calcFishCount(oldAge: Int, newAge: Int, fishForEachAge: Map<Int, Long>) =
    if (newAge == 6) calcFishAgedSix(fishForEachAge) else fishForEachAge.getOrDefault(oldAge, 0)

fun calcFishAgedSix(fishMap: Map<Int, Long>) =
    fishMap.getOrDefault(7,0) + fishMap.getOrDefault(0,0)

fun parse(data:String):Map<Int, Long> =
    data.split(",")
        .map(String::toInt)
        .groupingBy { it }.eachCount()
        .toList().associate { Pair(it.first, it.second.toLong()) }
