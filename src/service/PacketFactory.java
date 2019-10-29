package service;

import net.*;
import util.ArrayUtil;

import java.util.Arrays;

public class PacketFactory {
    public static Packet constructPacket (byte [] rawData) {
        PcapHeader pcapHeader;
        EthernetPacket ethernetHeader;

        pcapHeader = new PcapHeader(Arrays.copyOfRange(rawData, 0, PcapHeader.PH_SIZE));

        byte[] rawPacket = ArrayUtil.sliceBytes(rawData, PcapHeader.PH_SIZE , rawData.length);

        ethernetHeader = new EthernetPacket(rawPacket);

        switch (ethernetHeader.getEthernetType()) {
            case 0x0806:
                return new ArpPacket(rawPacket);
            case 0x0800:
                IpPacket ipHeader = new IpPacket(rawPacket);
                switch (ipHeader.getProtocol()) {
                    case 0x01 :
                        return new IcmpPacket(rawPacket);
                    case 0x06 :
                        return new TcpPacket(rawPacket);
                    case 0x11:
                        return new UdpPacket(rawPacket);
                    default :
                        return null;
                }
            default:
                return null;
        }
    }
}
