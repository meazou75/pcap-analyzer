package net;

import util.ArrayUtil;
import util.HexUtil;

public class FtpPacket extends TcpPacket {
    protected byte[] data;

    public FtpPacket(byte[] data, int TcpHeaderSize) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN + TcpHeaderSize, data.length, true);
    }

    public String getType() {
        return "FTP";
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                              F T P      P A C K E T                         \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append(HexUtil.bytesToString(this.data));
        return stringBuilder.toString();
    }
}
