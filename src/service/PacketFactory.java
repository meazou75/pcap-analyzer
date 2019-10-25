package service;

import net.ArpPacket;
import net.EthernetPacket;
import net.Packet;
import net.PcapHeader;
import util.ArrayUtil;

import java.util.Arrays;

public class PacketFactory {
    public static Packet constructPacket (byte [] rawData) {
        PcapHeader pcapHeader;
        EthernetPacket ethernetPacket;

        pcapHeader = new PcapHeader(Arrays.copyOfRange(rawData, 0, PcapHeader.PH_SIZE));
        ethernetPacket = new EthernetPacket(ArrayUtil.sliceBytes(rawData, PcapHeader.PH_SIZE, EthernetPacket.ETH_HEADER_LEN));

        byte[] rawPacket = ArrayUtil.sliceBytes(rawData, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN, rawData.length);

        switch (ethernetPacket.getEthernetType()) {
            case 0x0806:
                return new ArpPacket(rawPacket);
            default:
                return null;
        }
    }
}
