package net;

import util.ArrayUtil;
import util.HexUtil;

public class ArpPacket extends EthernetPacket implements ArpProperties{

    protected byte[] data;

    public ArpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN, data.length);
    }

    public String getType() {
        return "ARP";
    }

    public byte[] getData() {
        return this.data;
    }

    public byte[] getRawHardwareType() {
        return ArrayUtil.sliceBytes(this.data, ARP_HW_TYPE_POS, ARP_ADDR_TYPE_LEN);
    }

    public int getHardwareType() {
        return HexUtil.bytesToDec(getRawHardwareType(), false);
    }

    public String getHardwareTypeName() {
        if(getHardwareType() == ARP_ETH_ADDR_CODE) {
            return "Ethernet";
        }
        return "Unknown";
    }

    public byte[] getRawProtocolType() {
        return ArrayUtil.sliceBytes(this.data, ARP_PR_TYPE_POS, ARP_ADDR_TYPE_LEN);
    }

    public int getProtocolType() {
        return HexUtil.bytesToDec(getRawProtocolType(), false);
    }

    public String getProtocolTypeName() {
        if(getProtocolType() == ARP_IP_ADDR_CODE) {
            return "IPv4";
        }
        return "Unknown";
    }

    public byte[] getRawHardwareSize() {
        return ArrayUtil.sliceBytes(this.data, ARP_HW_LEN_POS, ARP_ADDR_SIZE_LEN);
    }

    public int getHardwareSize() {
        return HexUtil.bytesToDec(getRawHardwareSize(), false);
    }

    public byte[] getRawProtocolSize() {
        return ArrayUtil.sliceBytes(this.data, ARP_PR_LEN_POS, ARP_ADDR_SIZE_LEN);
    }

    public int getProtocolSize() {
        return HexUtil.bytesToDec(getRawProtocolSize(), false);
    }

    public byte[] getRawOperationCode() {
        return ArrayUtil.sliceBytes(this.data, ARP_OP_POS, ARP_OP_LEN);
    }

    public int getOperationCode() {
        return HexUtil.bytesToDec(getRawOperationCode(), false);
    }

    public String getOperationCodeName() {
        if(getOperationCode() == ARP_OP_REQ_CODE) {
            return "Request";
        }
        if(getOperationCode() == ARP_OP_REP_CODE) {
            return "Reply";
        }
        return "Unknown";
    }

    public byte[] getRawSourceMacAddress() {
        return ArrayUtil.sliceBytes(this.data, ARP_S_HW_ADDR_POS, MacAddress.WIDTH);
    }

    public String getSourceMacAddress() {
        return MacAddress.extract(0, getRawSourceMacAddress());
    }

    public byte[] getRawSourceProtocolAddress() {
        return ArrayUtil.sliceBytes(this.data, ARP_S_PR_ADDR_POS, IpAddress.WIDTH);
    }

    public String getSourceProtocolAddress() {
        return IpAddress.toString(getRawSourceProtocolAddress());
    }

    public byte[] getRawDestinationMacAddress() {
        return ArrayUtil.sliceBytes(this.data, ARP_T_HW_ADDR_POS, MacAddress.WIDTH);
    }

    public String getDestinationMacAddress() {
        return MacAddress.extract(0, getRawDestinationMacAddress());
    }

    public byte[] getRawDestinationProtocolAddress() {
        return ArrayUtil.sliceBytes(this.data, ARP_T_PR_ADDR_POS, IpAddress.WIDTH);
    }

    public String getDestinationProtocolAddress() {
        return IpAddress.toString(getRawDestinationProtocolAddress());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("                             A R P     P A C K E T                           \n");
        stringBuilder.append("-----------------------------------------------------------------------------\n");
        stringBuilder.append("Hardware Type               : ").append(HexUtil.toString(getRawHardwareType())).append(" = ").append(getHardwareTypeName()).append("\n");
        stringBuilder.append("Protocol Type               : ").append(HexUtil.toString(getRawProtocolType())).append(" = ").append(getProtocolTypeName()).append("\n");
        stringBuilder.append("Hardware      Size          : ").append(HexUtil.toString(getRawHardwareSize())).append(" = ").append(getHardwareSize()).append("\n");
        stringBuilder.append("Protocol      Size          : ").append(HexUtil.toString(getRawProtocolSize())).append(" = ").append(getProtocolSize()).append("\n");
        stringBuilder.append("Op Code                     : ").append(HexUtil.toString(getRawOperationCode())).append(" = ").append(getOperationCodeName()).append("\n");
        stringBuilder.append("Source MacAddress           : ").append(getSourceMacAddress()).append("\n");
        stringBuilder.append("Source ProtocolAddress      : ").append(getSourceProtocolAddress()).append("\n");
        stringBuilder.append("Destination MacAddress      : ").append(getDestinationMacAddress()).append("\n");
        stringBuilder.append("Destination ProtocolAddress : ").append(getDestinationProtocolAddress()).append("\n");
        return stringBuilder.toString();
    }
}
