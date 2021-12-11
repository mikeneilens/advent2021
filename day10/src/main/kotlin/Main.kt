val closingBrace = mapOf( '[' to ']',  '(' to ')',  '{' to '}',  '<' to '>')
val valuesForBrace = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

typealias Stack = String

sealed class Result{
    class OK(val stack:Stack):Result()
    class Error(val illegalChar:Char):Result()
}

fun String.validateExpression() =
    fold(Result.OK("") as Result){ result, nextChar ->
        if (result is Result.OK)
            if (closingBrace.values.contains(nextChar)) {
                if (nextChar == result.stack.top() || result.stack.isEmpty()) Result.OK(result.stack.pop())
                else Result.Error(nextChar)
            } else Result.OK(result.stack.push(closingBrace.getValue(nextChar)) )
        else  result
    }

fun Stack.push(char:Char) = plus(char)
fun Stack.pop() = if (isEmpty()) this else dropLast(1)
fun Stack.top() = if (isEmpty()) this else last()

fun List<String>.findErrors() = map(String::validateExpression).filterIsInstance<Result.Error>()

fun valueForEachError(error: Result.Error) = valuesForBrace[error.illegalChar] ?: 0

fun partOne(data:List<String>):Int = data.findErrors().sumOf(::valueForEachError)

fun List<String>.findStackForIncompleteExpressions() = map(String::validateExpression)
    .filterIsInstance<Result.OK>()
    .filter{it.stack.isNotEmpty()}
    .map{it.stack.reversed()}

val characterScore = mapOf(')' to 1, ']' to 2, '}' to 3,'>' to 4)

fun String.calcScore() = fold(0L){total, char -> total * 5 + characterScore.getValue(char)}

fun List<Long>.middleScore() = get(size/2)

fun partTwo(data:List<String>) =
     data.findStackForIncompleteExpressions()
         .map(String::calcScore)
         .sorted()
         .middleScore()
