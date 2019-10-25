package net;

import util.HexUtil;

import java.util.Arrays;
import java.util.Date;

public class PcapHeader implements  PcapHProperties{
    protected byte[] data;

    public PcapHeader(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }

    public Date getDateFromTimestamp() {
        return new Date((long) HexUtil.bytesToDec(getRawTimestamp(), true)*1000);
    }

    public byte[] getRawTimestamp() {
        return Arrays.copyOfRange(this.data, PcapHeader.PH_TIMESTAMP_POS, PcapHeader.PH_TIMESTAMP_LEN);
    }

    public int getPacketLenght() {
        return HexUtil.bytesToDec(getRawPacketLenght(), true);
    }

    public byte[] getRawPacketLenght() {
        return Arrays.copyOfRange(this.data, PcapHeader.PH_PACKETLENGHT_POS, PcapHeader.PH_PACKETLENGHT_POS + PcapHeader.PH_PACKETLENGHT_LEN);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                             P C A P     H E A D E R                         \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append(this.getData().toString()).append("\n\n");
        stringBuilder.append("Timestamp     : ").append(this.getRawTimestamp().toString()).append(" - ").append(this.getDateFromTimestamp()).append("\n");
        stringBuilder.append("Packet Lenght : ").append(this.getRawPacketLenght().toString()).append(" - ").append(this.getPacketLenght()).append("\n");
        return stringBuilder.toString();
    }
}
