package net;

public interface ArpProperties {

    int ARP_ETH_ADDR_CODE = 0x0001;

    int ARP_IP_ADDR_CODE = 0x0800;

    int ARP_OP_REQ_CODE = 0x1;

    int ARP_OP_REP_CODE = 0x2;



    int ARP_OP_LEN = 2;
    int ARP_ADDR_TYPE_LEN = 2;
    int ARP_ADDR_SIZE_LEN = 1;


    int ARP_HW_TYPE_POS = 0;
    int ARP_PR_TYPE_POS = ARP_HW_TYPE_POS + ARP_ADDR_TYPE_LEN;
    int ARP_HW_LEN_POS = ARP_PR_TYPE_POS + ARP_ADDR_TYPE_LEN;
    int ARP_PR_LEN_POS = ARP_HW_LEN_POS + ARP_ADDR_SIZE_LEN;
    int ARP_OP_POS = ARP_PR_LEN_POS + ARP_ADDR_SIZE_LEN;
    int ARP_S_HW_ADDR_POS = ARP_OP_POS + ARP_OP_LEN;
    int ARP_S_PR_ADDR_POS = ARP_S_HW_ADDR_POS + MacAddress.WIDTH;
    int ARP_T_HW_ADDR_POS = ARP_S_PR_ADDR_POS + IpAddress.WIDTH;
    int ARP_T_PR_ADDR_POS = ARP_T_HW_ADDR_POS + MacAddress.WIDTH;


    int ARP_HEADER_LEN = ARP_T_PR_ADDR_POS + IpAddress.WIDTH;
}
