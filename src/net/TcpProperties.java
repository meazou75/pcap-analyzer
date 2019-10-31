package net;

public interface TcpProperties {

    int TCP_PORT_LEN = 2;
    int TCP_SEQ_LEN = 4;
    int TCP_ACK_LEN = 4;
    int TCP_FLAG_LEN = 2;
    int TCP_WIN_LEN = 2;
    int TCP_CSUM_LEN = 2;
    int TCP_URG_LEN = 2;

    int TCP_SP_POS = 0;
    int TCP_DP_POS = TCP_PORT_LEN;
    int TCP_SEQ_POS = TCP_DP_POS + TCP_PORT_LEN;
    int TCP_ACK_POS = TCP_SEQ_POS + TCP_SEQ_LEN;
    int TCP_FLAG_POS = TCP_ACK_POS + TCP_ACK_LEN;
    int TCP_WIN_POS = TCP_FLAG_POS + TCP_FLAG_LEN;
    int TCP_CSUM_POS = TCP_WIN_POS + TCP_WIN_LEN;
    int TCP_URG_POS = TCP_CSUM_POS + TCP_CSUM_LEN;

    int TCP_HEADER_LEN = TCP_URG_POS + TCP_URG_LEN; // == 20
}

