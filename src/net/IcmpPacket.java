package net;

public class IcmpPacket extends IpPacket implements IcmpProperties {
    public IcmpPacket(byte[] data) {
        super(data);
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }
}
