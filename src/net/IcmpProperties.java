package net;

public interface IcmpProperties {
    int ICMP_CODE_LEN = 1;
    int ICMP_SUBC_LEN = 1;
    int ICMP_CSUM_LEN = 2;

    int ICMP_CODE_POS = 0;
    int ICMP_SUBC_POS = ICMP_CODE_POS + ICMP_CODE_LEN;
    int ICMP_CSUM_POS = ICMP_SUBC_POS + ICMP_CODE_LEN;

    int ICMP_HEADER_LEN = ICMP_CSUM_POS + ICMP_CSUM_LEN; // == 4
}
