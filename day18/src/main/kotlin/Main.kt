
sealed class SnailFish(var parent:SnailFish?) {
    abstract fun magnitude():Int
}

class SFPair(var p1:SnailFish, var p2:SnailFish, parent:SnailFish?):SnailFish(parent) {
    override fun magnitude(): Int = 3 * p1.magnitude() + 2 * p2.magnitude()
}

class RegularNumber(val num:Int, parent: SnailFish?):SnailFish(parent) {
    override fun magnitude() = num
}

fun newSnakeFish(parent:SnailFish?): SFPair = SFPair(RegularNumber(-999,null),RegularNumber(-99,null),parent)

operator fun SnailFish.plus(other:SnailFish):SnailFish {
    val sfPair = SFPair(this, other,null)
    this.parent = sfPair
    other.parent = sfPair
    explodeAndSplit(sfPair)
    return sfPair
}

fun List<SnailFish>.sum() = reduce{total, snailFish -> total + snailFish }

fun parse(s:String, parent:SnailFish? = null):SnailFish {
    val expression = s.drop(1).dropLast(1)
    val firstExpressionEnd  = if (expression.first() == '[') findExpressionEnd(expression) else 0
    val secondExpressionStart = firstExpressionEnd + 2

    val secondExpressionEnd = if (expression[secondExpressionStart] == '[') findExpressionEnd(expression.drop(secondExpressionStart)) + secondExpressionStart
    else firstExpressionEnd + 2

    val p1Expression = expression.substring(0,firstExpressionEnd + 1)
    val p2Expression = expression.substring(secondExpressionStart,secondExpressionEnd + 1)
    val sfPair = newSnakeFish(parent)
    sfPair.p1 = if (p1Expression.first() == '[') parse(p1Expression, sfPair) else RegularNumber(p1Expression.toInt(),sfPair)
    sfPair.p2 = if (p2Expression.first() == '[') parse(p2Expression,sfPair) else RegularNumber(p2Expression.toInt(),sfPair)
    return sfPair
}

fun SnailFish.text():String =
    if (this is RegularNumber) "${this.num}"
    else {
        val p1 = (this as SFPair).p1.text()
        val p2 = this.p2.text()
        "[$p1,$p2]"
    }

data class ExplodersAndSplitters(val exploder:SFPair?, val left:RegularNumber?, val right:RegularNumber?, val splitter:RegularNumber?)

fun findExplodersAndSplitters(snailFish: SnailFish):ExplodersAndSplitters {
    var exploder:SFPair? = null
    var regularNumber1:RegularNumber? = null
    var regularNumber2:RegularNumber? = null
    var splitter:RegularNumber? = null

    fun find(snailFish: SnailFish, level:Int = 1) {
        when {
            (snailFish is SFPair) -> {
                if (exploder == null && level == 5 ) exploder = snailFish
                find(snailFish.p1, level + 1)
                find(snailFish.p2, level + 1)
                return
            }
            (snailFish is RegularNumber) -> {
                if (exploder == null ) regularNumber1 = snailFish
                if (exploder != null && regularNumber2 == null  && snailFish.parent != exploder) regularNumber2 = snailFish
                if (splitter == null && snailFish.num > 9 ) splitter = snailFish
                return
            }
        }
    }

    find(snailFish)
    return ExplodersAndSplitters(exploder, regularNumber1, regularNumber2,splitter)
}

fun explode(exploder: SFPair, left:RegularNumber?, right:RegularNumber? ) {
    if (left?.parent != null) {
        val newLeftNum = left.num + (exploder.p1 as RegularNumber).num
        replaceSnailFish(left,RegularNumber(newLeftNum, left.parent) )
    }

    if (right?.parent != null) {
        val newRightNum = right.num + (exploder.p2 as RegularNumber).num
        replaceSnailFish(right,RegularNumber(newRightNum, right.parent) )
    }

    replaceSnailFish(exploder,RegularNumber(0, exploder.parent) )
}

fun replaceSnailFish(oldSnailFish:SnailFish, newSnailFish:SnailFish) {
    if (oldSnailFish.parent is SFPair) {
        val parent = oldSnailFish.parent as SFPair
        if (parent.p1 == oldSnailFish) parent.p1 = newSnailFish
        if (parent.p2 == oldSnailFish) parent.p2 = newSnailFish
    }
}

fun split(splitter:RegularNumber) {
    val newSFPair = newSnakeFish(splitter.parent)

    newSFPair.p1 = RegularNumber(splitter.num/2, newSFPair)

    newSFPair.p2 = if (splitter.num % 2 == 0 ) RegularNumber(splitter.num/2, newSFPair)
                    else RegularNumber(splitter.num/2 + 1, newSFPair)

    replaceSnailFish(splitter, newSFPair)
}

fun explodeAndSplit(snailFish: SnailFish) {
    var notFinished = true
    while (notFinished) {
        val (exploder, left, right, splitter) = findExplodersAndSplitters(snailFish)
        when {
            (exploder != null) -> explode(exploder, left, right)
            (splitter != null) -> split(splitter)
            else -> notFinished = false
        }
    }
}

fun findExpressionEnd(expression: String):Int {
    var openings = 0
    var ndx = 0
    var closingPosition = 0
    while (closingPosition == 0) {
        if (expression[ndx] == '[') openings++
        if (expression[ndx] == ']') openings--
        if (openings == 0) closingPosition = ndx else ndx++
    }
    return closingPosition
}

fun partOne(data:List<String>):Int =  data.map(::parse).sum().magnitude()

fun partTwo(data:List<String>):Int =
    data.indices.flatMap { m -> data.indices.map{ n->
        (parse(data[m]) + parse(data[n])).magnitude()
    } }.maxOf { it}
