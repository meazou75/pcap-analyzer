package net;

import util.ArrayUtil;
import util.HexUtil;

public class GlobalHeader implements GlobalProperties {

    protected byte[] data;

    public GlobalHeader(byte[] data) {
        this.data = data;
    }

    public boolean isValidPcap() {
        return HexUtil.bytesToHex(ArrayUtil.sliceBytes(this.data, 0, 4), true).equals("A1B2C3D4");
    }

    @Override
    public String toString() {
        return HexUtil.toString(this.data);
    }
}
