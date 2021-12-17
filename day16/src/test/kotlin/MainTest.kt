import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `version of 110100101111111000101000 is 6 `() {
        assertEquals(6,"110100101111111000101000".version)
    }
    @Test
    fun `typeId of 110100101111111000101000 is 4 `() {
        assertEquals(4,"110100101111111000101000".typeId)
    }
    @Test
    fun `first number in the literal 110100101111111000101000 is 0111`() {
        val (bin, moreData ) = "110100101111111000101000".getNumber(0)
        assertEquals(bin, "0111")
        assertTrue(moreData)
    }
    @Test
    fun `second number in the literal 110100101111111000101000 is 1110`() {
        val (bin, moreData ) = "110100101111111000101000".getNumber(1)
        assertEquals(bin, "1110")
        assertTrue(moreData)
    }
    @Test
    fun `third number in the literal 110100101111111000101000 is 0101`() {
        val (bin, moreData ) = "110100101111111000101000".getNumber(2)
        assertEquals(bin, "0101")
        assertTrue(!moreData)
    }
    @Test
    fun `converting 110100101111111000101000 to a packet`(){
        val packet = "110100101111111000101000".toPacket() as Packet.Literal
        assertEquals("011111100101", packet.num)
    }
    @Test
    fun `lengthTypeId of 00111000000000000110111101000101001010010001001000000000 is zero`() {
        assertEquals(0, "00111000000000000110111101000101001010010001001000000000".lengthTypeId)
    }
    @Test
    fun `length of subpackets of 00111000000000000110111101000101001010010001001000000000 is 15`() {
        assertEquals(27, "00111000000000000110111101000101001010010001001000000000".lengthOfSubPackets)
    }
    @Test
    fun `lengthTypeId of 11101110000000001101010000001100100000100011000001100000 is 1`() {
        assertEquals(1, "11101110000000001101010000001100100000100011000001100000".lengthTypeId)
    }
    @Test
    fun `  110100101111111000101000 as literal packet`() {
        val packets = "110100101111111000101000".toPacket()
        assertEquals(2, packets.size)
        assertEquals(6, packets[0].version)
        assertEquals("011111100101", (packets[0] as Packet.Literal).num)
        assertEquals(21, packets[0].noOfBits())
        assertEquals(3, (packets[1] as Packet.VoidPacket).length)
    }
    @Test
    fun `  00111000000000000110111101000101001010010001001000000000 as operator packet with fixed length containing two literals`() {
        val packets = "00111000000000000110111101000101001010010001001000000000".toPacket()
        assertEquals(2, packets.size,"top level packet size")
        assertEquals(1, packets[0].version,"top level version")
        val subPackets = (packets[0] as Packet.Operator1).subPackets
        assertEquals(2, subPackets.size, "subpacket size")
        assertEquals(6, subPackets[0].version)
        assertEquals(10, (subPackets[0] as Packet.Literal).num.toInt(2))
        assertEquals(2, subPackets[1].version)
        assertEquals(20, (subPackets[1] as Packet.Literal).num.toInt(2))
        assertEquals(7, (packets[1] as Packet.VoidPacket).length)
    }

    @Test
    fun `  11101110000000001101010000001100100000100011000001100000 as operator packet containing three sub packets`() {
        val packets = "11101110000000001101010000001100100000100011000001100000".toPacket()
        assertEquals(2, packets.size,"top level packet size")
        assertEquals(7, packets[0].version, "top level version")
        val subPackets = (packets[0] as Packet.Operator2).subPackets
        assertEquals(3, subPackets.size,"number of sub packets")
        assertEquals(2, subPackets[0].version, "sub packet[0] version")
        assertEquals(1, (subPackets[0] as Packet.Literal).num.toInt(2))
        assertEquals(4, subPackets[1].version,"sub packet[1] version")
        assertEquals(2, (subPackets[1] as Packet.Literal).num.toInt(2))
        assertEquals(1, subPackets[2].version,"sub packet[2] version")
        assertEquals(3, (subPackets[2] as Packet.Literal).num.toInt(2))
        assertEquals(5, (packets[1] as Packet.VoidPacket).length)
//        11101110000000001101010000001100100000100011000001100000
//                          01010000001100100000100011000001100000
//        VVVTTTILLLLLLLLLLLAAAAAAAAAAABBBBBBBBBBBCCCCCCCCCCC
    }
    @Test
    fun `version sum`(){
        val packets = "11101110000000001101010000001100100000100011000001100000".toPacket()
        assertEquals(7 + 2 + 4 + 1, packets.first().versionSum())
    }

    @Test
    fun `part one using samples`() {
        val d1= "8A004A801A8002F478".hexToBin()
        assertEquals(16, d1.toPacket().first().versionSum())
        val d2= "620080001611562C8802118E34".hexToBin()
        assertEquals(12, d2.toPacket().first().versionSum())
        val d3= "C0015000016115A2E0802F182340".hexToBin()
        assertEquals(23, d3.toPacket().first().versionSum())
        val d4= "A0016C880162017C3686B18A3D4780".hexToBin()
        assertEquals(31, d4.toPacket().first().versionSum())
        //d2 data
        // operator2  v=3       operator1 v=0        literal v=0 literal v=5 operator2 v=1      literal v=0 literal v=3
        // 011000100000000010 0000000000000000010110 00010001010 10110001011 001000100000000010 00010001100 0111000110100
        // VVVTTTILLLLLLLLLLL VVVTTTILLLLLLLLLLLLLLL VVVTTT----- VVVTTT----- VVVTTTILLLLLLLLLLL VVVTTT----- VVVTTT-------
        //
    }

    @Test
    fun `part one using puzzle input`() {
        val bin = puzzleInput.hexToBin()
        assertEquals(, bin.toPacket().first().versionSum())
    }

}
