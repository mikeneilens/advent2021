
const val versionBits = 3
const val typeIdBits = 3
const val lengthTypeIdBits = 1
const val totalLengthBits = 15
const val numberOfSubPacketsBits = 11

val String.version get() = take(versionBits).toInt(2)
val String.typeId get() = drop(versionBits).take(typeIdBits).toInt(2)
val String.lengthTypeId get() = drop(versionBits + typeIdBits).take(lengthTypeIdBits).toInt(2)
val String.lengthOfSubPackets get() = drop( versionBits + typeIdBits + lengthTypeIdBits).take(totalLengthBits).toInt(2)
val String.numberOfSubPackets get() = drop( versionBits + typeIdBits + lengthTypeIdBits).take(numberOfSubPacketsBits).toInt(2)

typealias ValueCalculator = (List<Packet>) -> Long

sealed class Packet(val version:Int, val typeId:Int) {

    abstract fun versionSum():Int
    abstract fun noOfBits():Int
    abstract fun typeCalc():Long

    class Literal(version:Int, typeId:Int, val num:String, private val parts:Int):Packet(version, typeId) {
        override fun versionSum(): Int = version
        override fun noOfBits() = headerBits + parts * 5
        override fun typeCalc() = num.toLong(2)

        companion object {
            const val headerBits = versionBits + typeIdBits
            fun getBits(data:String, n:Int) = data.drop(headerBits + n * 5).take(5)
        }
    }

    abstract class Operator(version:Int, typeId:Int, val subPackets:List<Packet>):Packet(version, typeId) {
        override fun versionSum() = version + subPackets.sumOf { it.versionSum() }
        override fun typeCalc() = valueCalculators.getValue(typeId)(subPackets)
    }

    class Operator1(version:Int, typeId:Int, subPackets:List<Packet>):Operator(version, typeId, subPackets) {
        override fun noOfBits() = op1HeaderBits + subPackets.sumOf { it.noOfBits() }

        companion object { const val op1HeaderBits = versionBits + typeIdBits + lengthTypeIdBits + totalLengthBits }
    }

    class Operator2(version:Int, typeId:Int, subPackets:List<Packet>):Operator(version, typeId, subPackets) {
        override fun noOfBits() = op2HeaderBits + subPackets.sumOf { it.noOfBits() }

        companion object { const val op2HeaderBits = versionBits + typeIdBits + lengthTypeIdBits + numberOfSubPacketsBits }
    }

    class VoidPacket(version:Int, val length:Int, typeId:Int = 0):Packet(version, typeId) {
        override fun versionSum() = 0
        override fun noOfBits() = length
        override fun typeCalc() = 0L
    }

    companion object {
        val valueCalculators:Map<Int, ValueCalculator> = mapOf(
            0 to {packets ->  packets.sumOf { it.typeCalc() }},
            1 to {packets -> packets.fold(1L){ total, literal -> total * literal.typeCalc()}},
            2 to {packets -> packets.minOf{it.typeCalc()}},
            3 to {packets -> packets.maxOf{it.typeCalc()}},
            5 to {packets -> if(packets[0].typeCalc() >  packets[1].typeCalc()) 1L else 0L},
            6 to {packets -> if(packets[0].typeCalc() <  packets[1].typeCalc()) 1L else 0L},
            7 to {packets -> if(packets[0].typeCalc() == packets[1].typeCalc()) 1L else 0L},
        )
    }
}

fun String.toPacket(maxQty:Int = Int.MAX_VALUE):List<Packet> {
    val packets = mutableListOf<Packet>()
    var data = this
    while (packets.size < maxQty && data.isNotEmpty()) {
        val version = data.version
        if (data.all{it=='0'}) return packets + Packet.VoidPacket(version,data.length)
        val packet = when (val typeId = data.typeId) {
            4 -> {
                val (value, numberOfParts) = getValueAndNoOfPartsOfLiteral(data)
                Packet.Literal(version, typeId, value,numberOfParts)
            }
            else -> {
                if (data.lengthTypeId == 0) {
                    val subPacketsLength = data.lengthOfSubPackets
                    Packet.Operator1(version, typeId, data.drop(Packet.Operator1.op1HeaderBits).take(subPacketsLength).toPacket())
                } else {
                    val numberOfSubPackets = data.numberOfSubPackets
                    Packet.Operator2(version, typeId, data.drop(Packet.Operator2.op2HeaderBits).toPacket(numberOfSubPackets))
                }
            }
        }
        packets.add(packet)
        data = data.drop(packet.noOfBits())

    }
    return packets
}

fun getValueAndNoOfPartsOfLiteral(data: String): Pair<String, Int> {
    var moreData = true
    var value = ""
    var numberOfParts = 0
    while (moreData) {
        val bits = Packet.Literal.getBits(data, numberOfParts)
        value += bits.drop(1).take(4)
        moreData = bits.first() == '1'
        numberOfParts++
    }
    return Pair(value, numberOfParts)
}

fun partOne(data:String):Int {
    val binaryData = data.hexToBin()
    return binaryData.toPacket().first().versionSum()
}

fun partTwo(data:String):Long {
    val binaryData = data.hexToBin()
    return binaryData.toPacket().first().typeCalc()
}

fun String.hexToBin():String {
    val hexToBin = mapOf('0' to "0000", '1' to "0001", '2' to "0010", '3' to "0011",
        '4' to "0100", '5' to "0101", '6' to "0110", '7' to "0111",
        '8' to "1000", '9' to "1001", 'A' to "1010", 'B' to "1011",
        'C' to "1100",'D' to "1101", 'E' to "1110", 'F' to "1111")
    return map{hexToBin.getValue(it)}.joinToString("")
}