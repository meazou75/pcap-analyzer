package net;

import util.ArrayUtil;
import util.HexUtil;

public class UdpPacket extends IpPacket implements UdpProperties {
    protected byte[] data;

    public UdpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN, data.length);
    }

    public byte[] getRawSourcePort() {
        return ArrayUtil.sliceBytes(this.data, UdpPacket.UDP_SP_POS, UdpPacket.UDP_PORT_LEN);
    }

    public int getSourcePort() {
        return HexUtil.bytesToDec(getRawSourcePort(), false);
    }

    public byte[] getRawDestinationPort() {
        return ArrayUtil.sliceBytes(this.data, UdpPacket.UDP_DP_POS, UdpPacket.UDP_PORT_LEN);
    }

    public int getDestinationPort() {
        return HexUtil.bytesToDec(getRawDestinationPort(), false);
    }

    public byte[] getRawLenght() {
        return ArrayUtil.sliceBytes(this.data, UdpPacket.UDP_LEN_POS, UdpPacket.UDP_LEN_LEN);
    }

    public int getLenght() {
        return HexUtil.bytesToDec(getRawLenght(), false);
    }

    public byte[] getRawChecksum() {
        return ArrayUtil.sliceBytes(this.data, UdpPacket.UDP_CSUM_POS, UdpPacket.UDP_CSUM_LEN);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                            U D P      P A C K E T                           \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("Source port               : ").append(HexUtil.toString(getRawSourcePort())).append(" = ").append(getSourcePort()).append("\n");
        stringBuilder.append("Destination port          : ").append(HexUtil.toString(getRawDestinationPort())).append(" = ").append(getDestinationPort()).append("\n");
        stringBuilder.append("Lenght                    : ").append(HexUtil.toString(getRawLenght())).append(" = ").append(getLenght()).append("\n");
        stringBuilder.append("Checksum                  : ").append(HexUtil.toString(getRawChecksum())).append("\n");
        return stringBuilder.toString();
    }

}
