fun partOne(data:String, days:Int):Long =
    (1..days).fold(parse(data),::makeFishOlder).sumOf{it}

fun makeFishOlder(fishForEachAge: List<Long>, day:Int) =
    ((1..8) + 0).mapIndexed { newAge, oldAge -> calcFishCount(oldAge, newAge, fishForEachAge) }

fun calcFishCount(oldAge: Int, newAge: Int, fishForEachAge: List<Long>) =
    if (newAge == 6) calcFishAgedSix(fishForEachAge) else fishForEachAge[oldAge]

fun calcFishAgedSix(fishMap: List<Long>) =
    fishMap[7] + fishMap[0]

fun parse(data:String) =(0..8).map{fishAge->  data.split(",").count { it.toInt() == fishAge}.toLong()}