package net;

import util.ArrayUtil;

public class TcpPacket extends IpPacket implements TcpProperties {

    protected byte[] data;

    public TcpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN, data.length);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                            T C P      P A C K E T                           \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        return stringBuilder.toString();
    }
}
