package client;

import net.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Viewer {
    public static void view_packets(ArrayList<Packet> packets) {
        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        int i = 0;
        String lastaction = "n";
        do{
            clearScreen();
            displayHeader();
            displayGlobalSummary(packets);
            displayHelp(i, packets.size());
            System.out.println(packets.get(i));
            String input = keyboard.nextLine();
            if(input != null) {
                if ("exit".equals(input)) {
                    System.out.println("Exit programm");
                    exit = true;
                } else if ("n".equals(input)) {
                    if(i < packets.size() -1) {
                        i++;
                    }
                    lastaction = input;
                } else if ("p".equals(input)) {
                    if(i > 0) {
                        i--;
                    }
                    lastaction = input;
                } else if ("".equals(input)) {
                    if(i > 0 && lastaction.equals("p")) {
                        i--;
                    }
                    if(i < packets.size()-1 && lastaction.equals("n")) {
                        i++;
                    }
                }
            }
        }while (!exit);
        keyboard.close();
    }

    public static void displayHeader() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("------P C A P     A N A L Y Z E R    by    D E L V I L L E   F R A N C O I S-------");
        System.out.println("-----------------------------------------------------------------------------------");
    }

    private static void displayGlobalSummary(ArrayList<Packet> packets) {
        StringBuilder stringBuilder = new StringBuilder();
        if (countArpPackets(packets) > 0) {
            stringBuilder.append("------ARP  : ").append(countArpPackets(packets)).append("\n");
        }
        if (countDhcpPackets(packets) > 0) {
            stringBuilder.append("------DHCP : ").append(countDhcpPackets(packets)).append("\n");
        }
        if (countDnsPackets(packets) > 0) {
            stringBuilder.append("----- DNS  : ").append(countDnsPackets(packets)).append("\n");
        }
        if (countFtpPackets(packets) > 0) {
            stringBuilder.append("----- FTP  : ").append(countFtpPackets(packets)).append("\n");
        }
        if (countHttpPackets(packets) > 0) {
            stringBuilder.append("----- HTTP : ").append(countHttpPackets(packets)).append("\n");
        }
        if (countIcmpPackets(packets) > 0) {
            stringBuilder.append("----- ICMP : ").append(countIcmpPackets(packets)).append("\n");
        }
        if (countIpPackets(packets) > 0) {
            stringBuilder.append("----- IPv4 : ").append(countIpPackets(packets)).append("\n");
        }
        if (countTcpPackets(packets) > 0) {
            stringBuilder.append("----- TCP  : ").append(countTcpPackets(packets)).append("\n");
        }
        if (countUdpPackets(packets) > 0) {
            stringBuilder.append("----- UDP  : ").append(countUdpPackets(packets)).append("\n");
        }
        System.out.print(stringBuilder.toString());
        System.out.println("-----------------------------------------------------------------------------------");
    }

    private static void displayHelp(int i, int max) {
        System.out.println("    exit -> Exit | n -> Next Package | p -> Previous Package | (empty) -> Recall   ");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("                              P A C K A G E     "+(i+1)+" / "+max);
    }

    private static int countArpPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof ArpPacket){
                i++;
            }
        }
        return i;
    }

    private static int countIcmpPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof IcmpPacket){
                i++;
            }
        }
        return i;
    }

    private static int countDhcpPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof DhcpPacket){
                i++;
            }
        }
        return i;
    }

    private static int countDnsPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof DnsPacket){
                i++;
            }
        }
        return i;
    }

    private static int countFtpPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof FtpPacket){
                i++;
            }
        }
        return i;
    }

    private static int countHttpPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof HttpPacket){
                i++;
            }
        }
        return i;
    }

    private static int countIpPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof IpPacket){
                i++;
            }
        }
        return i;
    }

    private static int countTcpPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof TcpPacket){
                i++;
            }
        }
        return i;
    }

    private static int countUdpPackets(ArrayList<Packet> packets) {
        int i = 0;
        for (Packet packet : packets) {
            if(packet instanceof UdpPacket){
                i++;
            }
        }
        return i;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
