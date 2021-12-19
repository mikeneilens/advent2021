
abstract class SnailFish(var parent:SnailFish?) {
    abstract fun magnitude():Int
}

class SFPair(var p1:SnailFish, var p2:SnailFish, parent:SnailFish?):SnailFish(parent) {
    override fun magnitude(): Int = 3 * p1.magnitude() + 2 * p2.magnitude()
}

class RegularNumber(val num:Int, parent: SnailFish?):SnailFish(parent) {
    override fun magnitude() = num
}

operator fun SnailFish.plus(other:SnailFish):SnailFish {
    val sfPair = SFPair(this,other,null)
    this.parent = sfPair
    other.parent = sfPair
    explodeAndSplit(sfPair)
    return sfPair
}

fun List<SnailFish>.sum() = reduce{total, sf -> total + sf }

fun parse(s:String, parent:SnailFish? = null):SnailFish {
    val expression = s.drop(1).dropLast(1)
    val firstExpressionEnd  = if (expression.first() == '[') findExpressionEnd(expression) else 0
    val secondExpressionStart = firstExpressionEnd + 2

    val secondExpressionEnd = if (expression[secondExpressionStart] == '[') findExpressionEnd(expression.drop(secondExpressionStart)) + secondExpressionStart
    else firstExpressionEnd + 2

    val p1Expression = expression.substring(0,firstExpressionEnd + 1)
    val p2Expression = expression.substring(secondExpressionStart,secondExpressionEnd + 1)
    val sfPair = SFPair(RegularNumber(1,null),RegularNumber(1,null),parent)
    sfPair.p1 = if (p1Expression.first() == '[') parse(p1Expression, sfPair) else RegularNumber(p1Expression.toInt(),sfPair)
    sfPair.p2 = if (p2Expression.first() == '[') parse(p2Expression,sfPair) else RegularNumber(p2Expression.toInt(),sfPair)
    return sfPair
}

fun SnailFish.text():String {
    if (this is RegularNumber) return "${this.num}"
    val p1 = (this as SFPair).p1.text()
    val p2 = this.p2.text()
    return "[$p1,$p2]"
}

data class ExplodersAndSplitters(val exploder:SFPair?, val left:RegularNumber?, val right:RegularNumber?, val splitter:RegularNumber?)

fun findExplodersAndSplitters(snailFish: SnailFish):ExplodersAndSplitters {
    var exploder:SFPair? = null
    var regularNumber1:RegularNumber? = null
    var regularNumber2:RegularNumber? = null
    var splitter:RegularNumber? = null

    fun find(snailFish: SnailFish, level:Int = 1) {
        if (snailFish is SFPair) {
            if (exploder == null && level == 5 ) {
                exploder = snailFish
            }
            find(snailFish.p1, level + 1)
            find(snailFish.p2, level + 1)
            return
        } else {
            if (snailFish is RegularNumber) {
                if (exploder == null )
                    regularNumber1 = snailFish
                if (exploder != null && regularNumber2 == null  && snailFish.parent != exploder)
                    regularNumber2 = snailFish
                if (splitter == null && snailFish.num > 9 ) {
                    splitter = snailFish
                }
            }
            return
        }
    }
    find(snailFish)
    return ExplodersAndSplitters(exploder, regularNumber1, regularNumber2,splitter)
}

fun explode(exploder: SFPair, left:RegularNumber?, right:RegularNumber? ) {
    val newLeftNum = (left?.num ?: 0) + (exploder.p1 as RegularNumber).num
    val newRightNum = (right?.num ?: 0) + (exploder.p2 as RegularNumber).num
    if (exploder.parent is SFPair) {
        val exploderParent = exploder.parent as SFPair
        if (exploderParent.p1 == exploder) exploderParent.p1 = RegularNumber(0, exploder.parent)
        if (exploderParent.p2 == exploder) exploderParent.p2 = RegularNumber(0, exploder.parent)
    }
    if (left?.parent is SFPair) {
        val leftParent = left.parent as SFPair
        if (leftParent.p1 == left)
            leftParent.p1 = RegularNumber(newLeftNum, left.parent)
        if (leftParent.p2 == left)
            leftParent.p2 = RegularNumber(newLeftNum, left.parent)
    }
    if (right?.parent is SFPair) {
        val rightParent = right.parent as SFPair
        if (rightParent.p1 == right)
            rightParent.p1 = RegularNumber(newRightNum, right.parent)
        if (rightParent.p2 == right)
            rightParent.p2 = RegularNumber(newRightNum, right.parent)
    }
}

fun split(splitter:RegularNumber) {
    if(splitter.parent is SFPair) {
        val splitterParent = splitter.parent as SFPair
        if (splitterParent.p1 == splitter)
            splitterParent.p1 = splitter.splitInTwo()
        if (splitterParent.p2 == splitter)
            splitterParent.p2 = splitter.splitInTwo()
    }
}

fun RegularNumber.splitInTwo():SFPair {
    val newSFPair = SFPair(RegularNumber(1,null),RegularNumber(1,null),parent)
    newSFPair.p1 = RegularNumber(num/2, newSFPair)
    if (num % 2 == 0 ) newSFPair.p2 = RegularNumber(num/2, newSFPair) else newSFPair.p2 = RegularNumber(num/2 + 1, newSFPair)
    return newSFPair
}

fun explodeAndSplit(snailFish: SnailFish) {
    var notFinished = true
    while (notFinished) {
        val (exploder, left, right, splitter) = findExplodersAndSplitters(snailFish)
        if (exploder != null) explode(exploder, left, right)
        else if (splitter != null) split(splitter)
        if (exploder == null && splitter == null) notFinished = false
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

fun partOne(data:List<String>):Int =  data.map{parse(it,null)}.sum().magnitude()

fun partTwo(data:List<String>):Int =
    data.indices.map { m -> data.indices.map{ n->
        (parse(data[m],null) + parse(data[n],null)).magnitude()
    } }.maxOf { it.maxOf { it } }
