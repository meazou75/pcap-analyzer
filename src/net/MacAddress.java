package net;

import util.HexUtil;

public class MacAddress {
    public static String extract(int offset, byte [] bytes) {
        StringBuffer sa = new StringBuffer();
        for(int i=offset; i<offset + WIDTH; i++) {
            sa.append(HexUtil.toString(bytes[i]));
            if(i != offset + WIDTH - 1)
                sa.append(':');
        }
        return sa.toString();
    }

    public static final int WIDTH = 6;
}
