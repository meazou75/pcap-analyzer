package client;

import util.FileUtil;
import util.HexUtil;

import java.io.IOException;

public class Extractor {
    public static void main(String[] args) throws IOException {
        byte[] buffer = FileUtil.extractDataFromFile("/home/user/Bureau/pcap-analyzer/ressources/ARP.pcap");



        System.out.println(HexUtil.toString(buffer));
    }
}
