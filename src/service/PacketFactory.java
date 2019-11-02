package service;

import net.*;
import util.ArrayUtil;
import util.HexUtil;

import java.util.Arrays;

public class PacketFactory {
    public static Packet constructPacket (byte [] rawData) {
        PcapHeader pcapHeader;
        EthernetPacket ethernetHeader;

        pcapHeader = new PcapHeader(rawData);

       // byte[] rawPacket = ArrayUtil.sliceBytes(rawData, PcapHeader.PH_SIZE , rawData.length, true);

        ethernetHeader = new EthernetPacket(rawData);

        switch (ethernetHeader.getEthernetType()) {
            case 0x0806:
                return new ArpPacket(rawData);
            case 0x0800:
                IpPacket ipHeader = new IpPacket(rawData);
                switch (ipHeader.getProtocol()) {
                    case 0x01 :
                        return new IcmpPacket(rawData);
                    case 0x06 :
                        return new TcpPacket(rawData);
                    case 0x11:
                        return new UdpPacket(rawData);
                    default :
                        return null;
                }
            default:
                return null;
        }
    }
}
