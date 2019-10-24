package net;

import util.HexUtil;

public class GlobalHeader implements GlobalProperties {

    protected byte[] data;

    public GlobalHeader(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return HexUtil.toString(this.data);
    }
}
