package service;

import net.*;
import util.ArrayUtil;

import java.util.Arrays;

public class PacketFactory {
    public static Packet constructPacket (byte [] rawData) {
        PcapHeader pcapHeader;
        EthernetPacket ethernetHeader;

        pcapHeader = new PcapHeader(Arrays.copyOfRange(rawData, 0, PcapHeader.PH_SIZE));
        ethernetHeader = new EthernetPacket(ArrayUtil.sliceBytes(rawData, PcapHeader.PH_SIZE, EthernetPacket.ETH_HEADER_LEN));

        byte[] rawPacket = ArrayUtil.sliceBytes(rawData, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN, rawData.length);

        switch (ethernetHeader.getEthernetType()) {
            case 0x0806:
                return new ArpPacket(rawPacket);
            case 0x0800:
                IpPacket ipHeader = new IpPacket(ArrayUtil.sliceBytes(rawPacket, 0, IpProperties.IP_HEADER_LEN));
                // Remove IpHeader from raw data
                rawPacket = ArrayUtil.sliceBytes(rawPacket, IpProperties.IP_HEADER_LEN, rawPacket.length);
                switch (ipHeader.getProtocol()) {
                    case 0x01 :
                        return new IcmpPacket(ArrayUtil.sliceBytes(rawPacket, 0, 10));
                    case 0x06 :
                        return ipHeader;
                    case 0x11:
                        return ipHeader;
                    default :
                        return ipHeader;
                }
            default:
                return ethernetHeader;
        }
    }
}
