package net;

public interface UdpProperties {

    int UDP_PORT_LEN = 2;
    int UDP_LEN_LEN = 2;
    int UDP_CSUM_LEN = 2;

    int UDP_SP_POS = 0;
    int UDP_DP_POS = UDP_PORT_LEN;
    int UDP_LEN_POS = UDP_DP_POS + UDP_PORT_LEN;
    int UDP_CSUM_POS = UDP_LEN_POS + UDP_LEN_LEN;

    int UDP_HEADER_LEN = UDP_CSUM_POS + UDP_CSUM_LEN; // == 8
}
