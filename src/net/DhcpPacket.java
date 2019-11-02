package net;

import util.ArrayUtil;

public class DhcpPacket extends UdpPacket {

    protected byte[] data;

    public DhcpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN + UdpPacket.UDP_HEADER_LEN, data.length, true);
    }

    public String getType() {
        return "DHCP";
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                            D H C P      P A C K E T                         \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        return stringBuilder.toString();
    }
}
