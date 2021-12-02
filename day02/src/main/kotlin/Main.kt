
sealed class Instruction(val qty:Int) {
    class Forward(qty:Int):Instruction(qty)
    class Up(qty:Int):Instruction(qty)
    class Down(qty:Int):Instruction(qty)

    companion object {
        fun create(s:String):Instruction {
            return when (s.command) {
                "forward" -> Forward(s.qty)
                "up" -> Up(s.qty)
                "down" -> Down(s.qty)
                else -> Down(0)
            }
        }
    }
    override fun equals(other: Any?) = other is Instruction && this.qty == other.qty  &&  this.javaClass == other.javaClass
}

val String.command get() = split(" ").first()
val String.qty get() = split(" ").last().toInt()

data class Status(val x:Int = 0, val y:Int=0, val aim:Int=0) {
    fun total() = x * y
}

fun partOne(data:List<String>) =
     data.map(Instruction::create)
         .fold(Status(),::executeInstruction)
         .total()

fun executeInstruction(current:Status, instruction:Instruction) = when(instruction) {
    is Instruction.Forward -> Status(x = current.x + instruction.qty, y = current.y, aim = current.aim)
    is Instruction.Up -> Status(x = current.x, y = current.y - instruction.qty, aim = current.aim)
    is Instruction.Down -> Status(x = current.x, y = current.y + instruction.qty ,aim = current.aim)
}

fun partTwo(data:List<String>) =
    data.map(Instruction::create)
        .fold(Status(),::executeInstrution)
        .total()

fun executeInstrution(current:Status, instruction:Instruction):Status = when (instruction) {
    is Instruction.Forward -> Status(x = current.x + instruction.qty ,y = current.y + current.aim * instruction.qty, aim = current.aim)
    is Instruction.Down -> Status(x = current.x, y = current.y, current.aim + instruction.qty)
    is Instruction.Up -> Status(x = current.x, y = current.y, aim = current.aim - instruction.qty)
}
