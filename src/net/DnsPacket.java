package net;

import util.ArrayUtil;

public class DnsPacket extends UdpPacket {
    protected byte[] data;

    public DnsPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN + UdpPacket.UDP_HEADER_LEN, data.length, true);
    }

    public String getType() {
        return "DNS";
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                              D N S      P A C K E T                         \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        return stringBuilder.toString();
    }
}
