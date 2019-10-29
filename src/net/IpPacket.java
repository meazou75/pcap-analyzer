package net;

import util.ArrayUtil;
import util.HexUtil;

import java.util.Arrays;

public class IpPacket extends EthernetPacket implements IpProperties {

    protected byte[] data;

    public IpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, EthernetPacket.ETH_HEADER_LEN, IpProperties.IP_HEADER_LEN);
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

    public byte[] getRawFlags() {
        return ArrayUtil.sliceBytes(this.data, IP_FRAG_POS, IP_FRAG_LEN);
    }

    public boolean getReservedFlag() {
        return ((getRawFlags()[0] >> 7) & 1) == 1 ;
    }

    public boolean getNoFragmentFlag() {
        return ((getRawFlags()[0] >> 6) & 1) == 1 ;
    }

    public boolean getMoreFragmentsFlag() {
        return ((getRawFlags()[0] >> 5) & 1) == 1 ;
    }

    public String getFlagsFormated() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Reserved Bit : ").append(getReservedFlag()).append(" / ");
        stringBuilder.append("Don't Fragment Bit : ").append(getNoFragmentFlag()).append(" / ");
        stringBuilder.append("More Fragments Bit : ").append(getMoreFragmentsFlag());
        return stringBuilder.toString();
    }

    public byte[] getRawTimeToLive() {
        return ArrayUtil.sliceBytes(this.data, IP_TTL_POS, IP_TTL_LEN);
    }

    public int getTimeToLive() {
        return HexUtil.bytesToDec(getRawTimeToLive(), false);
    }

    public byte[] getRawProtocol() {
        return ArrayUtil.sliceBytes(this.data, IP_CODE_POS, IP_CODE_LEN);
    }

    public int getProtocol() {
        return HexUtil.bytesToDec(getRawProtocol(), false);
    }

    public String getProtocolFormated() {
        switch (getProtocol()) {
            case 0x01 :
                return "ICMP";
            case 0x06 :
                return "TCP";
            case 0x11:
                return "UDP";
            default :
                return "Unknown";
        }
    }

    public byte[] getRawHeaderChecksum() {
        return ArrayUtil.sliceBytes(this.data, IP_CSUM_POS, IP_CSUM_LEN);
    }

    public byte[] getRawSourceAddress() {
        return ArrayUtil.sliceBytes(this.data, IP_SRC_POS, IpAddress.WIDTH);
    }

    public String getSourceAddress() {
        return IpAddress.toString(getRawSourceAddress());
    }

    public byte[] getRawDestinationAddress() {
        return ArrayUtil.sliceBytes(this.data, IP_DST_POS, IpAddress.WIDTH);
    }

    public String getDestinationAddress() {
        return IpAddress.toString(getRawDestinationAddress());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                           I P     H E A D E R                             \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("Version & Header Length  : ").append(HexUtil.toString(getRawVersionAndHeaderLength())).append("\n");
        stringBuilder.append("Type Of Service          : ").append(HexUtil.toString(getRawTypeOfService())).append("\n");
        stringBuilder.append("Total Length             : ").append(HexUtil.toString(getRawTotalLenght())).append(" = ").append(getTotalLenght()).append("\n");
        stringBuilder.append("Identification           : ").append(HexUtil.toString(getRawId())).append(" = ").append(getId()).append("\n");
        stringBuilder.append("Flags :                  : ").append(HexUtil.toString(getRawFlags())).append(" = ").append(getFlagsFormated()).append("\n");
        stringBuilder.append("Time to live             : ").append(HexUtil.toString(getRawTimeToLive())).append(" = ").append(getTimeToLive()).append("\n");
        stringBuilder.append("Protocol                 : ").append(getProtocolFormated()).append("\n");
        stringBuilder.append("Header Checksum          : ").append(HexUtil.toString(getRawHeaderChecksum())).append("\n");
        stringBuilder.append("Source Address           : ").append(getSourceAddress()).append("\n");
        stringBuilder.append("Destination Address      : ").append(getDestinationAddress()).append("\n");
        return stringBuilder.toString();
    }
}
