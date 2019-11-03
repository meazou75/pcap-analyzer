package net;

import util.ArrayUtil;
import util.HexUtil;

public class TcpPacket extends IpPacket implements TcpProperties {

    protected byte[] data;

    public TcpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN, data.length, true);
    }

    public String getType() {
        return "TCP";
    }

    public byte[] getRawSourcePort() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_SP_POS, TcpPacket.TCP_PORT_LEN);
    }

    public int getSourcePort() {
        return HexUtil.bytesToDec(getRawSourcePort(), false);
    }

    public byte[] getRawDestinationPort() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_DP_POS, TcpPacket.TCP_PORT_LEN);
    }

    public int getDestinationPort() {
        return HexUtil.bytesToDec(getRawDestinationPort(), false);
    }

    public byte[] getRawSequenceNumber() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_SEQ_POS, TcpPacket.TCP_SEQ_LEN);
    }

    public long getSequenceNumber() {
        return HexUtil.bytesToLong(getRawSequenceNumber(), false);
    }

    public byte[] getRawAcknowledgmentNumber() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_ACK_POS, TcpPacket.TCP_ACK_LEN);
    }

    public long getAcknowledgmentNumber() {
        return HexUtil.bytesToLong(getRawAcknowledgmentNumber(), false);
    }

    public byte[] getRawTcpFlags() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_FLAG_POS, TcpPacket.TCP_FLAG_LEN);
    }

    public int getHeaderLenght() {
        return HexUtil.byteToDec(getRawTcpFlags()[0], 0, 4) * 4;
    }

    public boolean isNonceFlagSet() {
        return ((getRawTcpFlags()[0] >> 0) & 1) == 1;
    }

    public boolean isCwrFlagSet() {
        return ((getRawTcpFlags()[1] >> 7) & 1) == 1;
    }

    public boolean isEcnFlagSet() {
        return ((getRawTcpFlags()[1] >> 6) & 1) == 1;
    }

    public boolean isUrgentFlagSet() {
        return ((getRawTcpFlags()[1] >> 5) & 1) == 1;
    }

    public boolean isAckFlagSet() {
        return ((getRawTcpFlags()[1] >> 4) & 1) == 1;
    }

    public boolean isPushFlagSet() {
        return ((getRawTcpFlags()[1] >> 3) & 1) == 1;
    }

    public boolean isResetFlagSet() {
        return ((getRawTcpFlags()[1] >> 2) & 1) == 1;
    }

    public boolean isSynFlagSet() {
        return ((getRawTcpFlags()[1] >> 1) & 1) == 1;
    }

    public boolean isFinFlagSet() {
        return ((getRawTcpFlags()[1] >> 0) & 1) == 1;
    }

    public String getTcpFlagsFormated() {
        StringBuilder stringBuilder = new StringBuilder();
        if(isNonceFlagSet()) stringBuilder.append("Nonce / ");
        if(isCwrFlagSet()) stringBuilder.append("CWR / ");
        if(isEcnFlagSet()) stringBuilder.append("ECN Echo / ");
        if(isAckFlagSet()) stringBuilder.append("ACK / ");
        if(isPushFlagSet()) stringBuilder.append("PUSH / ");
        if(isResetFlagSet()) stringBuilder.append("RESET / ");
        if(isSynFlagSet()) stringBuilder.append("SYN / ");
        if(isFinFlagSet()) stringBuilder.append("FIN");
        return stringBuilder.toString();
    }

    public byte[] getRawWindowSize() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_WIN_POS, TcpPacket.TCP_WIN_LEN);
    }

    public byte[] getRawChecksum() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_CSUM_POS, TcpPacket.TCP_CSUM_LEN);
    }

    public byte[] getRawUrgentPointer() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_URG_POS, TcpPacket.TCP_URG_LEN);
    }

    private boolean hasOptions() {
        return getHeaderLenght() > 20;
    }

    public byte[] getRawOptions() {
        return ArrayUtil.sliceBytes(this.data, TcpPacket.TCP_HEADER_LEN, getHeaderLenght() - 20);
    }

    public boolean hasPayload() {
        return this.data.length - getHeaderLenght() != 0;
    }

    public byte[] getRawPayload() {
        return ArrayUtil.sliceBytes(this.data, getHeaderLenght(), this.data.length, true);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------------\n");
        stringBuilder.append("------------------------T C P      P A C K E T                           \n");
        stringBuilder.append("-----------------------------------------------------------------------------------\n");
        stringBuilder.append("Source port               : ").append(HexUtil.toString(getRawSourcePort())).append(" = ").append(getSourcePort()).append("\n");
        stringBuilder.append("Destination port          : ").append(HexUtil.toString(getRawDestinationPort())).append(" = ").append(getDestinationPort()).append("\n");
        stringBuilder.append("Sequence Number           : ").append(HexUtil.toString(getRawSequenceNumber())).append(" = ").append(getSequenceNumber()).append("\n");
        stringBuilder.append("Acknowledgment Number     : ").append(HexUtil.toString(getRawAcknowledgmentNumber())).append(" = ").append(getAcknowledgmentNumber()).append("\n");
        stringBuilder.append("Header Lenght             : ").append(getHeaderLenght()).append("\n");
        stringBuilder.append("Flags                     : ").append(getTcpFlagsFormated()).append("\n");
        stringBuilder.append("Window Size               : ").append(HexUtil.toString(getRawWindowSize())).append("\n");
        stringBuilder.append("Checksum                  : ").append(HexUtil.toString(getRawChecksum())).append("\n");
        stringBuilder.append("Urgent Pointer            : ").append(HexUtil.toString(getRawUrgentPointer())).append("\n");
        stringBuilder.append("Option                    : ").append(hasOptions() ? HexUtil.toString(getRawOptions()) : "None").append("\n");
        return stringBuilder.toString();
    }
}
