
const val versionBits = 3
const val typeIdBits = 3
const val lenghtTypeIdBits = 1
const val totalLengthBits = 15
const val numberOfSubPacketsBits = 11

val String.version get() = take(versionBits).toInt(2)
val String.typeId get() = drop(versionBits).take(typeIdBits).toInt(2)
val String.lengthTypeId get() = drop(versionBits + typeIdBits).take(lenghtTypeIdBits).toString().toInt(2)
val String.lengthOfSubPackets get() = drop( versionBits + typeIdBits + lenghtTypeIdBits).take(totalLengthBits).toInt(2)
val String.numberOfSubPackets get() = drop( versionBits + typeIdBits + lenghtTypeIdBits).take(numberOfSubPacketsBits).toInt(2)

fun String.getNumber(n:Int):Pair<String, Boolean> {
    val bin = drop(6 + n * 5).take(5)
    return Pair(bin.drop(1).take(4), bin.first() == '1')
}

sealed class Packet(val version:Int) {

    abstract fun versionSum():Int
    abstract fun noOfBits():Int
    
    class Literal(version:Int, val num:String, val parts:Int):Packet(version) {
        override fun versionSum(): Int = version
        override fun noOfBits() = versionBits + typeIdBits + parts * 5
        
        companion object {
            
        }
    }
    class Operator1(version:Int, val subPackets:List<Packet>):Packet(version) {
        override fun versionSum() = version + subPackets.sumOf { it.versionSum() }
        override fun noOfBits() = versionBits + typeIdBits + lenghtTypeIdBits + totalLengthBits + subPackets.sumOf { it.noOfBits() }
    }
    class Operator2(version:Int, val subPackets:List<Packet>):Packet(version) {
        override fun versionSum() = version + subPackets.sumOf { it.versionSum() }
        override fun noOfBits() = versionBits + typeIdBits + lenghtTypeIdBits + numberOfSubPacketsBits + subPackets.sumOf { it.noOfBits() }
    }
    class VoidPacket(version:Int, val length:Int):Packet(version) {
        override fun versionSum() = 0
        override fun noOfBits() = length
    }

    override fun toString(): String {
        when(this) {
            is Literal -> return "Literal(version = $version, num = $num)"
            is Operator1 -> return "Operator1(version = $version, subPackets = $subPackets"
            is Operator2 -> return "Operator2(version = $version, subPackets = $subPackets"
            is VoidPacket -> return "VoidPacket(version = $version, length = $length"
        }
    }
}

fun String.toPacket(maxQty:Int = Int.MAX_VALUE):List<Packet> {
    val packets = mutableListOf<Packet>()
    var data = this
    while (packets.size < maxQty && data.isNotEmpty()) {
        val version = data.version
        if (data.all{it=='0'}) return packets + Packet.VoidPacket(version,data.length)
        val typeId = data.typeId
        when (typeId) {
            4 -> {
                var moreData = true
                var num = ""
                var n = 0
                while (moreData) {
                    num += data.getNumber(n).first
                    moreData = data.getNumber(n).second
                    n++
                }
                val packet = Packet.Literal(version, num,n)
                packets.add(packet)
                data = data.drop(packet.noOfBits())
            }
            else -> {
                if (data.lengthTypeId == 0) {
                    val subPacketsLength = data.lengthOfSubPackets
                    val subPacketData = data.drop(versionBits + typeIdBits + lenghtTypeIdBits + totalLengthBits)
                    val packet = Packet.Operator1(version, data.drop(versionBits + typeIdBits + lenghtTypeIdBits + totalLengthBits).take(subPacketsLength).toPacket())
                    packets.add(packet)
                    data = data.drop(packet.noOfBits())
                } else {
                    val numberOfSubPackets = data.numberOfSubPackets
                    val packet = Packet.Operator2(version, data.drop(versionBits + typeIdBits + lenghtTypeIdBits + numberOfSubPacketsBits).toPacket(maxQty = numberOfSubPackets))
                    packets.add(packet)
                    data = data.drop(packet.noOfBits())
                }
            }
        }
    }
    return packets
}

fun <T>MutableList<T>.removeFirst(n:Int) = (1..n).forEach { removeFirst() }

fun String.hexToBin():String {
    val hextToBin = mapOf('0' to "0000", '1' to "0001", '2' to "0010", '3' to "0011",
        '4' to "0100", '5' to "0101", '6' to "0110", '7' to "0111",
        '8' to "1000", '9' to "1001", 'A' to "1010", 'B' to "1011",
        'C' to "1100",'D' to "1101", 'E' to "1110", 'F' to "1111")
    return map{hextToBin.getValue(it)}.joinToString("")
}