
interface Instruction {
    val execute: (Submarine) -> Submarine
    val executeP2: (Submarine) -> Submarine
}

fun createInstruction(s: String): Instruction {
     return when (s.command) {
         "forward" -> Forward(s.qty)
         "up" -> Up(s.qty)
         "down" -> Down(s.qty)
         else -> Down(0)
     }
}
val String.command get() = split(" ").first()
val String.qty get() = split(" ").last().toInt()

class Forward(qty:Int):Instruction {
    override val execute = {current:Submarine -> Submarine(x = current.x + qty, y = current.y, aim = current.aim)}
    override val executeP2 = { current:Submarine -> Submarine(x = current.x + qty ,y = current.y + current.aim * qty, aim = current.aim)}
}
class Up(qty:Int):Instruction {
    override val execute = {current:Submarine -> Submarine(x = current.x , y = current.y - qty, aim = current.aim)}
    override val executeP2 = { current:Submarine -> Submarine(x = current.x, y = current.y, aim = current.aim - qty)}
}
class Down(qty:Int):Instruction {
    override val execute = {current:Submarine -> Submarine(x = current.x , y = current.y + qty, aim = current.aim)}
    override val executeP2 = { current:Submarine -> Submarine(x = current.x, y = current.y, current.aim + qty)}
}

data class Submarine(val x:Int = 0, val y:Int=0, val aim:Int=0) {
    fun total() = x * y
}

fun partOne(data:List<String>) =
     data.map(::createInstruction)
         .fold(Submarine()){submarine, instruction -> instruction.execute(submarine)}
         .total()

fun partTwo(data:List<String>) =
    data.map(::createInstruction)
        .fold(Submarine()){submarine, instruction -> instruction.executeP2(submarine)}
        .total()
