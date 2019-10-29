package client;

import net.*;
import service.PacketFactory;
import util.FileUtil;
import util.HexUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Extractor {
    public static void main(String[] args) throws IOException {
        byte[] buffer = FileUtil.extractDataFromFile("C:\\Users\\fdelville\\Desktop\\MSSIS-Projects\\pcap-analyzer\\ressources\\test3.pcap");

        GlobalHeader globalHeader = new GlobalHeader(Arrays.copyOfRange(buffer, 0, GlobalHeader.GLB_SIZE));

        // Tchek if Valid PCAP

        buffer = Arrays.copyOfRange(buffer, GlobalHeader.GLB_SIZE, buffer.length);

        ArrayList<byte[]> raw_buffer_sequenced = new ArrayList<>();

        int size = HexUtil.extractInteger(buffer, PcapHeader.PH_TOTALPACKETLENGHT_POS, PcapHeader.PH_TOTALPACKETLENGHT_LEN, true);

        while (size <= (buffer.length - PcapHeader.PH_SIZE) && size != 0) {
            raw_buffer_sequenced.add(Arrays.copyOfRange(buffer, 0, size + PcapHeader.PH_SIZE));
            buffer = Arrays.copyOfRange(buffer, size + PcapHeader.PH_SIZE, buffer.length);

            if(buffer.length < PcapHeader.PH_SIZE) {
                break;
            }

            size = HexUtil.extractInteger(buffer, PcapHeader.PH_TOTALPACKETLENGHT_POS, PcapHeader.PH_TOTALPACKETLENGHT_LEN, true);
        }

        ArrayList<Packet> packets = new ArrayList<>();

        for (byte[] raw_packet : raw_buffer_sequenced) {
            packets.add(PacketFactory.constructPacket(raw_packet));
        }

        for (Packet packet : packets) {
            /*if(packet instanceof IpPacket) {
               System.out.println(((IpPacket)packet).getFlagsFormated());
            }*/
            if(packet == null) {
                continue;
            }
           System.out.println(packet.toString());
           System.out.println("-----------------------------------------------------------------------------");
        }
    }
}
