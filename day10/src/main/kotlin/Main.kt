val closingBrace = mapOf( '[' to ']',  '(' to ')',  '{' to '}',  '<' to '>')
val valuesForBrace = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

sealed class Result{
    class OK(val stack:String):Result()
    class Error(val illegalChar:Char):Result()
}

fun validateExpression(expression:String, stack:String = ""):Result {
    if (expression.isEmpty()) return Result.OK(stack)
    val nextChar = expression.first()
    return if (closingBrace.values.contains(nextChar))
        if (stack.isEmpty()) validateExpression(expression.drop(1), stack)
        else if ( nextChar == stack.last()) validateExpression(expression.drop(1), stack.dropLast(1))
        else Result.Error(nextChar)
    else validateExpression(expression.drop(1), stack + closingBrace[expression.first()])
}

fun List<String>.findErrors() = map(::validateExpression).filterIsInstance<Result.Error>()

fun valueForEachError(error:Result.Error) = valuesForBrace[error.illegalChar] ?: 0

fun partOne(data:List<String>):Int = data.findErrors().map(::valueForEachError).sum()

