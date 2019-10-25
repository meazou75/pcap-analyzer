package net;

import util.ArrayUtil;
import util.HexUtil;

import java.util.Arrays;

public class EthernetPacket extends Packet implements EthernetProperties {
    protected byte[] data;

    public EthernetPacket(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }

    public byte[] getRawDestinationMacAddress() {
        return ArrayUtil.sliceBytes(this.data, EthernetPacket.ETH_DST_POS, EthernetPacket.ETH_DST_LEN);
    }

    public byte[] getRawSourceMacAddress() {
        return ArrayUtil.sliceBytes(this.data, EthernetPacket.ETH_SRC_POS, EthernetPacket.ETH_SRC_LEN);
    }

    public byte[] getRawEthernetType() {
        return ArrayUtil.sliceBytes(this.data, EthernetPacket.ETH_CODE_POS, EthernetPacket.ETH_CODE_LEN);
    }

    public String getDestinationMacAddress() {
        return MacAddress.extract(0, this.getRawDestinationMacAddress());
    }

    public String getSourceMacAddress() {
        return MacAddress.extract(0, this.getRawSourceMacAddress());
    }

    public int getEthernetType() {
        return HexUtil.bytesToDec(getRawEthernetType(), false);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                        E T H E R N E T     H E A D E R                      \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("Destination Mac Address     : ").append(getDestinationMacAddress()).append("\n");
        stringBuilder.append("Source Mac Address          : ").append(getSourceMacAddress()).append("\n");
        stringBuilder.append("Ethernet Type               : ").append(HexUtil.toString(this.getRawEthernetType())).append(" - ").append(getEthernetType()).append("\n");
        return stringBuilder.toString();
    }
}
