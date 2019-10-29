package net;

import util.ArrayUtil;

public class UdpPacket extends IpPacket implements UdpProperties {
    protected byte[] data;

    public UdpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN, data.length);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                            U D P      P A C K E T                           \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        return stringBuilder.toString();
    }

}
