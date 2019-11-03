package client;

import net.*;

import java.util.ArrayList;

public class Filter {
    public static ArrayList<Packet> filter_packets(ArrayList<Packet> packets, String filter) {
        ArrayList<Packet> result = new ArrayList<>();
        switch (filter) {
            case "IPV4":
                for (Packet packet : packets) {
                    if(packet instanceof IpPacket) result.add(packet);
                }
                return result;
            case "UDP":
                for (Packet packet : packets) {
                    if(packet instanceof UdpPacket) result.add(packet);
                }
                return result;
            case "TCP":
                for (Packet packet : packets) {
                    if(packet instanceof TcpPacket) result.add(packet);
                }
                return result;
            case "DHCP":
                for (Packet packet : packets) {
                    if(packet instanceof DhcpPacket) result.add(packet);
                }
                return result;
            case "HTTP":
                for (Packet packet : packets) {
                    if(packet instanceof HttpPacket) result.add(packet);
                }
                return result;
            case "DNS":
                for (Packet packet : packets) {
                    if(packet instanceof DnsPacket) result.add(packet);
                }
                return result;
            case "FTP":
                for (Packet packet : packets) {
                    if(packet instanceof FtpPacket) result.add(packet);
                }
                return result;
            case "ARP":
                for (Packet packet : packets) {
                    if(packet instanceof ArpPacket) result.add(packet);
                }
                return result;
            case "ICMP":
                for (Packet packet : packets) {
                    if(packet instanceof IcmpPacket) result.add(packet);
                }
                return result;
            default:
                return result;
        }
    }
}
