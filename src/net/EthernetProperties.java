package net;

public interface EthernetProperties {
    int ETH_CODE_LEN = 2;
    int ETH_DST_LEN = 6;
    int ETH_SRC_LEN = 6;

    int ETH_DST_POS  = 0;
    int ETH_SRC_POS  = MacAddress.WIDTH;
    int ETH_CODE_POS = 12;

    int ETH_HEADER_LEN = ETH_CODE_POS + ETH_CODE_LEN;
}
