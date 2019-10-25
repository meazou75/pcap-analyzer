package net;

public interface IpProperties {

    int IP_VER_LEN = 1;
    int IP_TOS_LEN = 1;
    int IP_LEN_LEN = 2;
    int IP_ID_LEN = 2;
    int IP_FRAG_LEN = 2;
    int IP_TTL_LEN = 1;
    int IP_CODE_LEN = 1;
    int IP_CSUM_LEN = 2;

    int IP_VER_POS = 0;
    int IP_TOS_POS = IP_VER_POS + IP_VER_LEN;
    int IP_LEN_POS = IP_TOS_POS + IP_TOS_LEN;
    int IP_ID_POS = IP_LEN_POS + IP_LEN_LEN;
    int IP_FRAG_POS = IP_ID_POS + IP_ID_LEN;
    int IP_TTL_POS = IP_FRAG_POS + IP_FRAG_LEN;
    int IP_CODE_POS = IP_TTL_POS + IP_TTL_LEN;
    int IP_CSUM_POS = IP_CODE_POS + IP_CODE_LEN;
    int IP_SRC_POS = IP_CSUM_POS + IP_CSUM_LEN;
    int IP_DST_POS = IP_SRC_POS + IpAddress.WIDTH;

    int IP_HEADER_LEN = IP_DST_POS + IpAddress.WIDTH; // == 20
}
