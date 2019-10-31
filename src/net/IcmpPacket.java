package net;

import util.ArrayUtil;
import util.HexUtil;

public class IcmpPacket extends IpPacket implements IcmpProperties {

    protected byte[] data;

    public IcmpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN, data.length);
    }

    public byte[] getData() {
        return this.data;
    }

    public byte[] getRawIcmpType() {
        return ArrayUtil.sliceBytes(this.data, ICMP_CODE_POS, ICMP_CODE_LEN);
    }

    public int getIcmpType() {
        return HexUtil.bytesToDec(getRawIcmpType(), false);
    }

    public String getIcmpTypeFormated() {
        if(getIcmpType() == 0x08) {
            return "Request";
        }
        if(getIcmpType() == 0x00) {
            return "Reply";
        }
        return "Unknown";
    }

    public byte[] getRawIcmpCode() {
        return ArrayUtil.sliceBytes(this.data, ICMP_SUBC_POS, ICMP_CODE_LEN);
    }

    public int getIcmpCode() {
        return HexUtil.bytesToDec(getRawIcmpCode(), false);
    }

    public byte[] getRawIcmpChecksum() {
        return ArrayUtil.sliceBytes(this.data, ICMP_CSUM_POS, ICMP_CSUM_LEN);
    }

    public byte[] getRawIcmpPayload() {
        return ArrayUtil.sliceBytes(this.data, ICMP_HEADER_LEN + 12, this.data.length);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                          I C M P     P A C K E T                            \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("Icmp Type                 : ").append(HexUtil.toString(getRawIcmpType())).append(" = ").append(getIcmpTypeFormated()).append("\n");
        stringBuilder.append("Icmp Code                 : ").append(HexUtil.toString(getRawIcmpCode())).append(" = ").append(getIcmpCode()).append("\n");
        stringBuilder.append("Icmp Checksum             : ").append(HexUtil.toString(getRawIcmpChecksum())).append("\n");
        stringBuilder.append("Icmp Data                 : ").append(HexUtil.toString(getRawIcmpPayload())).append("\n");
        return stringBuilder.toString();
    }
}
