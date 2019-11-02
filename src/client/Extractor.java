package client;

import net.*;
import service.PacketFactory;
import util.ArrayUtil;
import util.FileUtil;
import util.HexUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Extractor {
    public static void main(String[] args) throws IOException {

        List validTypes = Arrays.asList("ETHERNET", "IPV4", "UDP", "TCP", "DHCP", "HTTP", "DNS", "FTP", "ARP");

        if(args.length == 0) {
            System.out.println("No options specify please use -help ");
            return;
        }

        if(args[0].equals("-help")) {
            System.out.println("Project by Delville Francois");
            System.out.println("Usage : java -jar pcap-analyzer <fname> => Print all packets from the pcapfile");
            System.out.println("Usage : java -jar pcap-analyzer <fname> TCP => Print all TCP packets from the pcapfile");
            System.out.println("Available packet type : ETHERNET / ARP / IPV4 / UPD / TCP / DHCP / HTTP / DNS / FTP");
            return;
        }

        if(args.length == 2) {
            if (!validTypes.contains(args[1])){
                System.out.println("Type provided is not valid ! ");
                return;
            }
        }

        byte[] buffer = FileUtil.extractDataFromFile(args[0]);

        GlobalHeader globalHeader = new GlobalHeader(Arrays.copyOfRange(buffer, 0, GlobalHeader.GLB_SIZE));

        if(!globalHeader.isValidPcap()) {
            System.out.println("The file is not a pcap file (Wrong Magic Number)");
            return;
        }

        buffer = ArrayUtil.sliceBytes(buffer, GlobalHeader.GLB_SIZE, buffer.length, true);

        ArrayList<byte[]> raw_buffer_sequenced = new ArrayList<>();

        int size = HexUtil.extractInteger(buffer, PcapHeader.PH_TOTALPACKETLENGHT_POS, PcapHeader.PH_TOTALPACKETLENGHT_LEN, true);

        while (size <= (buffer.length - PcapHeader.PH_SIZE) && size != 0) {
            raw_buffer_sequenced.add(ArrayUtil.sliceBytes(buffer, 0, size + PcapHeader.PH_SIZE));
            buffer = ArrayUtil.sliceBytes(buffer, size + PcapHeader.PH_SIZE, buffer.length, true);

            if(buffer.length < PcapHeader.PH_SIZE) {
                break;
            }

            size = HexUtil.extractInteger(buffer, PcapHeader.PH_TOTALPACKETLENGHT_POS, PcapHeader.PH_TOTALPACKETLENGHT_LEN, true);
        }

        ArrayList<Packet> packets = new ArrayList<>();

        for (byte[] raw_packet : raw_buffer_sequenced) {
            packets.add(PacketFactory.constructPacket(raw_packet));
        }

        ArrayList<ArrayList<TcpPacket>> sessionsTCP = new ArrayList<>();
        ArrayList<ArrayList<UdpPacket>> sessionsUDP = new ArrayList<>();
        ArrayList<ArrayList<Packet>> sessionsOTH = new ArrayList<>();

        for (Packet packet : packets) {
            if(packet == null) {
                continue;
            }
            if(packet instanceof UdpPacket) {
                boolean added = false;
                for (ArrayList<UdpPacket> session : sessionsUDP) {
                    if(((UdpPacket)packet).getSourcePort() == session.get(0).getSourcePort() || ((UdpPacket)packet).getDestinationPort() == session.get(0).getSourcePort()) {
                        session.add(((UdpPacket)packet));
                        added = true;
                        break;
                    }
                }
                if(!added) {
                    sessionsUDP.add(new ArrayList<>(Collections.singletonList(((UdpPacket) packet))));
                }
                continue;
            }
            if(packet instanceof TcpPacket) {
                boolean added = false;
                for (ArrayList<TcpPacket> session : sessionsTCP) {
                    if(((TcpPacket)packet).getSourcePort() == session.get(0).getSourcePort() || ((TcpPacket)packet).getDestinationPort() == session.get(0).getSourcePort()) {
                        session.add(((TcpPacket)packet));
                        added = true;
                        break;
                    }
                }
                if(!added) {
                    sessionsTCP.add(new ArrayList<>(Collections.singletonList(((TcpPacket) packet))));
                }
                continue;
            }
            if(packet instanceof IpPacket) {
                boolean added = false;
                for (ArrayList<Packet> session : sessionsOTH) {
                    Packet test = session.get(0);
                    if(test instanceof IpPacket) {
                        if(((IpPacket) packet).getDestinationAddress().equals(((IpPacket) test).getDestinationAddress()) || ((IpPacket) packet).getSourceAddress().equals(((IpPacket) test).getDestinationAddress())) {
                            session.add(packet);
                            added = true;
                            break;
                        }
                    }
                }
                if(!added) {
                    sessionsOTH.add(new ArrayList<>(Collections.singletonList(packet)));
                }
                continue;
            }
            if(packet instanceof EthernetPacket) {
                boolean added = false;
                for (ArrayList<Packet> session : sessionsOTH) {
                    Packet test = session.get(0);
                    if(test instanceof EthernetPacket) {
                        if(((EthernetPacket) packet).getSourceMacAddress().equals(((EthernetPacket) test).getSourceMacAddress()) || ((EthernetPacket) packet).getSourceMacAddress().equals(((EthernetPacket) test).getDestinationMacAddress())) {
                            session.add(packet);
                            added = true;
                            break;
                        }
                    }
                }
                if(!added) {
                    sessionsOTH.add(new ArrayList<>(Collections.singletonList(packet)));
                }
            }
        }

        for (ArrayList<TcpPacket> tcpPackets : sessionsTCP) {
            String supposedType = "TCP";
            for (TcpPacket tcpPacket : tcpPackets) {
                if (tcpPacket.getSourcePort() == 80 || tcpPacket.getDestinationPort() == 80) {
                    supposedType = "HTTP";
                }
                if (tcpPacket.getSourcePort() == 21 || tcpPacket.getDestinationPort() == 21) {
                    supposedType = "FTP";
                }
                if (tcpPacket.hasPayload()) {
                    String payloadStringify = HexUtil.bytesToString(tcpPacket.getRawPayload());
                    if (payloadStringify.contains("RETR") || payloadStringify.contains("PASV") || payloadStringify.contains("FTP")) {
                        supposedType = "FTP";
                    }
                    if (payloadStringify.contains("HTTP") || payloadStringify.contains("http")) {
                        supposedType = "HTTP";
                    }
                }
            }
            for (int i=0; i<tcpPackets.size(); i++) {
                if(tcpPackets.get(i).hasPayload()) {
                    if(supposedType.equals("HTTP")) {
                        tcpPackets.set(i, new HttpPacket(tcpPackets.get(i)._rawdata, tcpPackets.get(i).getHeaderLenght()));
                    }
                    if(supposedType.equals("FTP")) {
                        tcpPackets.set(i, new FtpPacket(tcpPackets.get(i)._rawdata, tcpPackets.get(i).getHeaderLenght()));
                    }
                }
            }
        }

        for (ArrayList<UdpPacket> udpPackets : sessionsUDP) {
            String supposedType = "UDP";
            for (UdpPacket udpPacket : udpPackets) {
                if (udpPacket.getSourcePort() == 53 || udpPacket.getDestinationPort() == 53) {
                    supposedType = "DNS";
                }
                if (udpPacket.getSourcePort() == 67 || udpPacket.getDestinationPort() == 67) {
                    supposedType = "DHCP";
                }
            }
            for (int i=0; i<udpPackets.size(); i++) {
                if(udpPackets.get(i).hasPayload()) {
                    if(supposedType.equals("DNS")) {
                        udpPackets.set(i, new DnsPacket(udpPackets.get(i)._rawdata));
                    }
                    if(supposedType.equals("DHCP")) {
                        udpPackets.set(i, new DhcpPacket(udpPackets.get(i)._rawdata));
                    }
                }
            }
        }

        packets = new ArrayList<>();

        // Merge all packet

        for (ArrayList<TcpPacket> packet : sessionsTCP ) {
            packets.addAll(packet);
        }

        for (ArrayList<UdpPacket> packet : sessionsUDP ) {
            packets.addAll(packet);
        }

        for (ArrayList<Packet> packet : sessionsOTH ) {
            packets.addAll(packet);
        }

        // Sort packet by timestamp

        packets.sort((o1, o2) -> {
            if(o1 instanceof PcapHeader && o2 instanceof PcapHeader) {
                long difference = HexUtil.bytesToLong(((PcapHeader) o1).getRawUTimestamp(), true) - HexUtil.bytesToLong(((PcapHeader) o2).getRawUTimestamp(), true);
                if(difference == 0) {
                    return 0;
                }
                else if (difference < 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
            return -1;
        });

        for (Packet packet : packets) {
           System.out.println(packet.toString());
           System.out.println("-----------------------------------------------------------------------------");
        }
    }
}
