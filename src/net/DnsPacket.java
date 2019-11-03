package net;

import util.ArrayUtil;
import util.HexUtil;

public class DnsPacket extends UdpPacket implements DnsProperties {
    protected byte[] data;

    public DnsPacket(byte[] data) {
        super(data);
        this.data = ArrayUtil.sliceBytes(data, PcapHeader.PH_SIZE + EthernetPacket.ETH_HEADER_LEN + IpProperties.IP_HEADER_LEN + UdpPacket.UDP_HEADER_LEN, data.length, true);
    }

    public byte[] getRawTransactionId () {
        return ArrayUtil.sliceBytes(this.data, DnsPacket.DNS_TRANSID_POS, DnsPacket.DNS_TRANSID_LEN);
    }

    public byte[] getRawFlags () {
        return ArrayUtil.sliceBytes(this.data, DnsPacket.DNS_FLAGS_POS, DnsPacket.DNS_FLAGS_LEN);
    }

    public byte[] getRawDnsQuestions() {
        return ArrayUtil.sliceBytes(this.data, DnsPacket.DNS_QUESTIONS_POS, DNS_QUESTIONS_LEN);
    }

    public byte[] getRawDnsAnswers() {
        return ArrayUtil.sliceBytes(this.data, DnsPacket.DNS_ANSWERRR_POS, DNS_ANSWERRR_LEN);
    }

    public byte[] getRawDnsAuthority() {
        return ArrayUtil.sliceBytes(this.data, DnsPacket.DNS_AUTHORITYRR_POS, DNS_AUTHORITYRR_LEN);
    }

    public byte[] getRawDnsAdditional() {
        return ArrayUtil.sliceBytes(this.data, DnsPacket.DNS_ADDITRR_POS, DNS_ADDITRR_LEN);
    }

    public byte[] getRawQueries() {
        return ArrayUtil.sliceBytes(this.data, DNS_HEADER_LEN, this.data.length, true);
    }

    public String getType() {
        return "DNS";
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("-----------------------------------------------------------------------------------\n");
        stringBuilder.append("------------------------------D N S      P A C K E T                         \n");
        stringBuilder.append("-----------------------------------------------------------------------------------\n");
        stringBuilder.append("Transaction ID            : ").append(HexUtil.toString(getRawTransactionId())).append("\n");
        stringBuilder.append("Flags                     : ").append(HexUtil.toString(getRawFlags())).append("\n");
        stringBuilder.append("Questions                 : ").append(HexUtil.toString(getRawDnsQuestions())).append("\n");
        stringBuilder.append("Answer RRs                : ").append(HexUtil.toString(getRawDnsAnswers())).append("\n");
        stringBuilder.append("Authority RRs             : ").append(HexUtil.toString(getRawDnsAuthority())).append("\n");
        stringBuilder.append("Additional RRs            : ").append(HexUtil.toString(getRawDnsAdditional())).append("\n");
        stringBuilder.append("Queries (stringify)       : ").append(HexUtil.bytesToString(getRawQueries())).append("\n");
        return stringBuilder.toString();
    }
}
