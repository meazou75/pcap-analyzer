package net;

public class Packet {

    public byte[] _rawdata;

    Packet(byte[] data) {
        this._rawdata = data;
    }

    byte[] getData() {
        return null;
    }

    public String getType() {
        return "";
    }
}
