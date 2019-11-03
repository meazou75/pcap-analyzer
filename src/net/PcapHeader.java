package net;

import util.ArrayUtil;
import util.HexUtil;

import java.util.Arrays;
import java.util.Date;

public class PcapHeader extends Packet implements PcapHProperties{
    protected byte[] data;

    public PcapHeader(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, 0, PcapHeader.PH_SIZE);
    }

    public String getType() {
        return "PCAP";
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

    public byte[] getRawUTimestamp() {
        return ArrayUtil.sliceBytes(this.data, PcapHeader.PH_USTIMESTAMP_POS, PcapHeader.PH_USTIMESTAMP_LEN);
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
        stringBuilder.append("-----------------------------------------------------------------------------------\n");
        stringBuilder.append("------P C A P     H E A D E R                         \n");
        stringBuilder.append("-----------------------------------------------------------------------------------\n");
        stringBuilder.append("Timestamp     : ").append(HexUtil.toString(getRawTimestamp())).append(" - ").append(this.getDateFromTimestamp()).append("\n");
        stringBuilder.append("Packet Lenght : ").append(HexUtil.toString(getRawPacketLenght())).append(" - ").append(this.getPacketLenght()).append("\n");
        return stringBuilder.toString();
    }
}
