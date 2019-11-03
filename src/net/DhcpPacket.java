package net;

import util.ArrayUtil;
import util.HexUtil;

public class DhcpPacket extends UdpPacket implements DhcpProperties {

    protected byte[] data;

    public DhcpPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN + UdpPacket.UDP_HEADER_LEN, data.length, true);
    }

    public String getType() {
        return "DHCP";
    }

    public byte[] getRawMessageType() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_MSGTYPE_POS, DhcpPacket.DHCP_MSGTYPE_LEN);
    }

    public String getMessageTypeFormated() {
        if(HexUtil.bytesToDec(getRawMessageType(), false) == 0x2) {
            return "Boot Reply (2)";
        }
        if(HexUtil.bytesToDec(getRawMessageType(), false) == 0x1) {
            return "Boot Request (1)";
        }
        return "Unknown";
    }

    public byte[] getRawHardwareType() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_HRDTYPE_POS, DhcpPacket.DHCP_HRDTYPE_LEN);
    }

    public byte[] getRawHardwareAddrLenght() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_HRDLEN_POS, DhcpPacket.DHCP_HRDLEN_LEN);
    }

    public byte[] getRawHops() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_HOPS_POS, DhcpPacket.DHCP_HOPS_LEN);
    }

    public byte[] getRawTransactionId() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_TRANSID_POS, DhcpPacket.DHCP_TRANSID_LEN);
    }

    public byte[] getRawSecondsElapsed() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_SECELAP_POS, DhcpPacket.DHCP_SECELAP_LEN);
    }

    public byte[] getRawBootpFlags() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_BPFLAGS_POS, DhcpPacket.DHCP_BPFLAGS_LEN);
    }

    public byte[] getRawClientIpAddr() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_CLIENTIP_POS, DhcpPacket.DHCP_CLIENTIP_LEN);
    }

    public String getClientIpAddr() {
        return IpAddress.toString(getRawClientIpAddr());
    }

    public byte[] getRawOwnerIpAddr() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_OWNERIP_POS, DhcpPacket.DHCP_OWNERIP_LEN);
    }

    public String getOwnerIpAddr() {
        return IpAddress.toString(getRawOwnerIpAddr());
    }

    public byte[] getRawServerIpAddr() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_NSERVIP_POS, DhcpPacket.DHCP_NSERVIP_LEN);
    }

    public String getServerIpAddr() {
        return IpAddress.toString(getRawServerIpAddr());
    }

    public byte[] getRawRelayIpAddr() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_NRELAIP_POS, DhcpPacket.DHCP_NRELAIP_LEN);
    }

    public String getRelayIpAddr() {
        return IpAddress.toString(getRawRelayIpAddr());
    }

    public byte[] getRawClientMacAddr() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_CLIENTMAC_POS, DhcpPacket.DHCP_CLIENTMAC_LEN);
    }

    public String getClientMacAddr() {
        return MacAddress.extract(0, getRawClientMacAddr());
    }

    public byte[] getRawServerHostName() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_SERVERHOSTNAME_POS, DhcpPacket.DHCP_SERVERHOSTNAME_LEN);
    }

    public String getServerHostName() {
        if(HexUtil.bytesToDec(getRawServerHostName(), false) == 0x0) {
            return "Undefined";
        }
        return HexUtil.bytesToString(getRawServerHostName());
    }

    public byte[] getRawBootfileName() {
        return ArrayUtil.sliceBytes(this.data, DhcpPacket.DHCP_BOOTFILENAME_POS, DhcpPacket.DHCP_BOOTFILENAME_LEN);
    }

    public String getBootfileName() {
        if(HexUtil.bytesToDec(getRawBootfileName(), false) == 0x0) {
            return "Undefined";
        }
        return HexUtil.bytesToString(getRawBootfileName());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------------\n");
        stringBuilder.append("------------------------------D H C P      P A C K E T                         \n");
        stringBuilder.append("-----------------------------------------------------------------------------------\n");
        stringBuilder.append("Message Type              : ").append(getMessageTypeFormated()).append("\n");
        stringBuilder.append("Hardware Type             : ").append(HexUtil.toString(getRawHardwareType())).append("\n");
        stringBuilder.append("Hardware Address Length   : ").append(HexUtil.toString(getRawHardwareAddrLenght())).append("\n");
        stringBuilder.append("Hops                      : ").append(HexUtil.toString(getRawHops())).append("\n");
        stringBuilder.append("Seconds Elapsed           : ").append(HexUtil.toString(getRawSecondsElapsed())).append("\n");
        stringBuilder.append("Bootp Flags               : ").append(HexUtil.toString(getRawBootpFlags())).append("\n");
        stringBuilder.append("Client Ip Address         : ").append(getClientIpAddr()).append("\n");
        stringBuilder.append("Owner Ip Address          : ").append(getOwnerIpAddr()).append("\n");
        stringBuilder.append("Next Server Ip Address    : ").append(getServerIpAddr()).append("\n");
        stringBuilder.append("Next Relay Ip Address     : ").append(getRelayIpAddr()).append("\n");
        stringBuilder.append("Client Mac Address        : ").append(getClientMacAddr()).append("\n");
        stringBuilder.append("Server Host Name          : ").append(getServerHostName()).append("\n");
        stringBuilder.append("Bootfile Name             : ").append(getBootfileName()).append("\n");
        return stringBuilder.toString();
    }
}
