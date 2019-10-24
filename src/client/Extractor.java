package client;

import net.GlobalHeader;
import util.FileUtil;
import util.HexUtil;

import java.io.IOException;
import java.util.Arrays;

public class Extractor {
    public static void main(String[] args) throws IOException {
        byte[] buffer = FileUtil.extractDataFromFile("/home/user/Bureau/pcap-analyzer/ressources/ARP.pcap");

        GlobalHeader globalHeader = new GlobalHeader(Arrays.copyOfRange(buffer, 0, GlobalHeader.GLB_SIZE));

        System.out.println(HexUtil.toString(buffer));
        System.out.println(globalHeader.toString());
    }
}
