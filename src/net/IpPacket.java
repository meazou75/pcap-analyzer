package net;

import util.ArrayUtil;
import util.HexUtil;

public class IpPacket extends EthernetPacket implements IpProperties {

    protected byte[] data;

    public IpPacket(byte[] data) {
        super(data);
        this.data = data;
    }

    public byte[] getRawVersionAndHeaderLength() {
        return ArrayUtil.sliceBytes(this.data, IP_VER_POS, IP_VER_LEN);
    }

    public int getVersionAndHeaderLength() {
        return HexUtil.bytesToDec(getRawVersionAndHeaderLength(), false);
    }

    public byte[] getRawTypeOfService() {
        return ArrayUtil.sliceBytes(this.data, IP_TOS_POS, IP_TOS_LEN);
    }

    public String getTypeOfService() {
        return HexUtil.toString(getRawTypeOfService());
    }

    public byte[] getRawTotalLenght() {
        return ArrayUtil.sliceBytes(this.data, IP_LEN_POS, IP_LEN_LEN);
    }

    public int getTotalLenght() {
        return HexUtil.bytesToDec(getRawTotalLenght(), false);
    }

    public byte[] getRawId() {
        return ArrayUtil.sliceBytes(this.data, IP_ID_POS, IP_ID_LEN);
    }

    public int getId() {
        return HexUtil.bytesToDec(getRawId(), false);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                           I P     H E A D E R                             \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("Version & Header Length  : ").append(HexUtil.toString(getRawVersionAndHeaderLength())).append("\n");
        stringBuilder.append("Type Of Service          : ").append(HexUtil.toString(getRawTypeOfService())).append("\n");
        stringBuilder.append("Total Length             : ").append(HexUtil.toString(getRawTotalLenght())).append(" = ").append(getTotalLenght()).append("\n");
        stringBuilder.append("Identification           : ").append(HexUtil.toString(getRawId())).append(" = ").append(getId()).append("\n");
        return stringBuilder.toString();
    }
}
